package org.apache.logging.log4j.core.script;

import java.io.File;
import java.io.Serializable;
import java.nio.file.Path;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.util.FileWatcher;
import org.apache.logging.log4j.core.util.WatchManager;
import org.apache.logging.log4j.status.StatusLogger;

public class ScriptManager implements FileWatcher, Serializable {
	private static final long serialVersionUID = -2534169384971965196L;
	private static final String KEY_THREADING = "THREADING";
	private static final Logger logger = StatusLogger.getLogger();
	private final Configuration configuration;
	private final ScriptEngineManager manager = new ScriptEngineManager();
	private final ConcurrentMap<String, ScriptManager.ScriptRunner> scriptRunners = new ConcurrentHashMap();
	private final String languages;
	private final WatchManager watchManager;

	public ScriptManager(Configuration configuration, WatchManager watchManager) {
		this.configuration = configuration;
		this.watchManager = watchManager;
		List<ScriptEngineFactory> factories = this.manager.getEngineFactories();
		if (logger.isDebugEnabled()) {
			StringBuilder sb = new StringBuilder();
			logger.debug("Installed script engines");

			for (ScriptEngineFactory factory : factories) {
				String threading = (String)factory.getParameter("THREADING");
				if (threading == null) {
					threading = "Not Thread Safe";
				}

				StringBuilder names = new StringBuilder();

				for (String name : factory.getNames()) {
					if (names.length() > 0) {
						names.append(", ");
					}

					names.append(name);
				}

				if (sb.length() > 0) {
					sb.append(", ");
				}

				sb.append(names);
				boolean compiled = factory.getScriptEngine() instanceof Compilable;
				logger.debug(
					factory.getEngineName()
						+ " Version: "
						+ factory.getEngineVersion()
						+ ", Language: "
						+ factory.getLanguageName()
						+ ", Threading: "
						+ threading
						+ ", Compile: "
						+ compiled
						+ ", Names: {"
						+ names.toString()
						+ "}"
				);
			}

			this.languages = sb.toString();
		} else {
			StringBuilder names = new StringBuilder();

			for (ScriptEngineFactory factory : factories) {
				for (String name : factory.getNames()) {
					if (names.length() > 0) {
						names.append(", ");
					}

					names.append(name);
				}
			}

			this.languages = names.toString();
		}
	}

	public void addScript(AbstractScript script) {
		ScriptEngine engine = this.manager.getEngineByName(script.getLanguage());
		if (engine == null) {
			logger.error("No ScriptEngine found for language " + script.getLanguage() + ". Available languages are: " + this.languages);
		} else {
			if (engine.getFactory().getParameter("THREADING") == null) {
				this.scriptRunners.put(script.getName(), new ScriptManager.ThreadLocalScriptRunner(script));
			} else {
				this.scriptRunners.put(script.getName(), new ScriptManager.MainScriptRunner(engine, script));
			}

			if (script instanceof ScriptFile) {
				ScriptFile scriptFile = (ScriptFile)script;
				Path path = scriptFile.getPath();
				if (scriptFile.isWatched() && path != null) {
					this.watchManager.watchFile(path.toFile(), this);
				}
			}
		}
	}

	public Bindings createBindings(AbstractScript script) {
		return this.getScriptRunner(script).createBindings();
	}

	public AbstractScript getScript(String name) {
		ScriptManager.ScriptRunner runner = (ScriptManager.ScriptRunner)this.scriptRunners.get(name);
		return runner != null ? runner.getScript() : null;
	}

	@Override
	public void fileModified(File file) {
		ScriptManager.ScriptRunner runner = (ScriptManager.ScriptRunner)this.scriptRunners.get(file.toString());
		if (runner == null) {
			logger.info("{} is not a running script");
		} else {
			ScriptEngine engine = runner.getScriptEngine();
			AbstractScript script = runner.getScript();
			if (engine.getFactory().getParameter("THREADING") == null) {
				this.scriptRunners.put(script.getName(), new ScriptManager.ThreadLocalScriptRunner(script));
			} else {
				this.scriptRunners.put(script.getName(), new ScriptManager.MainScriptRunner(engine, script));
			}
		}
	}

	public Object execute(String name, Bindings bindings) {
		final ScriptManager.ScriptRunner scriptRunner = (ScriptManager.ScriptRunner)this.scriptRunners.get(name);
		if (scriptRunner == null) {
			logger.warn("No script named {} could be found");
			return null;
		} else {
			return AccessController.doPrivileged(new PrivilegedAction<Object>() {
				public Object run() {
					return scriptRunner.execute(bindings);
				}
			});
		}
	}

	private ScriptManager.ScriptRunner getScriptRunner(AbstractScript script) {
		return (ScriptManager.ScriptRunner)this.scriptRunners.get(script.getName());
	}

	private abstract class AbstractScriptRunner implements ScriptManager.ScriptRunner {
		private static final String KEY_STATUS_LOGGER = "statusLogger";
		private static final String KEY_CONFIGURATION = "configuration";

		private AbstractScriptRunner() {
		}

		@Override
		public Bindings createBindings() {
			SimpleBindings bindings = new SimpleBindings();
			bindings.put("configuration", ScriptManager.this.configuration);
			bindings.put("statusLogger", ScriptManager.logger);
			return bindings;
		}
	}

	private class MainScriptRunner extends ScriptManager.AbstractScriptRunner {
		private final AbstractScript script;
		private final CompiledScript compiledScript;
		private final ScriptEngine scriptEngine;

		public MainScriptRunner(ScriptEngine scriptEngine, AbstractScript script) {
			this.script = script;
			this.scriptEngine = scriptEngine;
			CompiledScript compiled = null;
			if (scriptEngine instanceof Compilable) {
				ScriptManager.logger.debug("Script {} is compilable", script.getName());
				compiled = (CompiledScript)AccessController.doPrivileged(new PrivilegedAction<CompiledScript>() {
					public CompiledScript run() {
						try {
							return ((Compilable)scriptEngine).compile(script.getScriptText());
						} catch (Throwable var2) {
							ScriptManager.logger.warn("Error compiling script", var2);
							return null;
						}
					}
				});
			}

			this.compiledScript = compiled;
		}

		@Override
		public ScriptEngine getScriptEngine() {
			return this.scriptEngine;
		}

		@Override
		public Object execute(Bindings bindings) {
			if (this.compiledScript != null) {
				try {
					return this.compiledScript.eval(bindings);
				} catch (ScriptException var3) {
					ScriptManager.logger.error("Error running script " + this.script.getName(), (Throwable)var3);
					return null;
				}
			} else {
				try {
					return this.scriptEngine.eval(this.script.getScriptText(), bindings);
				} catch (ScriptException var4) {
					ScriptManager.logger.error("Error running script " + this.script.getName(), (Throwable)var4);
					return null;
				}
			}
		}

		@Override
		public AbstractScript getScript() {
			return this.script;
		}
	}

	private interface ScriptRunner {
		Bindings createBindings();

		Object execute(Bindings bindings);

		AbstractScript getScript();

		ScriptEngine getScriptEngine();
	}

	private class ThreadLocalScriptRunner extends ScriptManager.AbstractScriptRunner {
		private final AbstractScript script;
		private final ThreadLocal<ScriptManager.MainScriptRunner> runners = new ThreadLocal<ScriptManager.MainScriptRunner>() {
			protected ScriptManager.MainScriptRunner initialValue() {
				ScriptEngine engine = ScriptManager.this.manager.getEngineByName(ThreadLocalScriptRunner.this.script.getLanguage());
				return ScriptManager.this.new MainScriptRunner(engine, ThreadLocalScriptRunner.this.script);
			}
		};

		public ThreadLocalScriptRunner(AbstractScript script) {
			this.script = script;
		}

		@Override
		public Object execute(Bindings bindings) {
			return ((ScriptManager.MainScriptRunner)this.runners.get()).execute(bindings);
		}

		@Override
		public AbstractScript getScript() {
			return this.script;
		}

		@Override
		public ScriptEngine getScriptEngine() {
			return ((ScriptManager.MainScriptRunner)this.runners.get()).getScriptEngine();
		}
	}
}
