import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public interface acs {
	DecimalFormat a = v.a(new DecimalFormat("########0.00"), decimalFormat -> decimalFormat.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT)));
	acs b = NumberFormat.getIntegerInstance(Locale.US)::format;
	acs c = integer -> a.format((double)integer * 0.1);
	acs d = integer -> {
		double double2 = (double)integer / 100.0;
		double double4 = double2 / 1000.0;
		if (double4 > 0.5) {
			return a.format(double4) + " km";
		} else {
			return double2 > 0.5 ? a.format(double2) + " m" : integer + " cm";
		}
	};
	acs e = integer -> {
		double double2 = (double)integer / 20.0;
		double double4 = double2 / 60.0;
		double double6 = double4 / 60.0;
		double double8 = double6 / 24.0;
		double double10 = double8 / 365.0;
		if (double10 > 0.5) {
			return a.format(double10) + " y";
		} else if (double8 > 0.5) {
			return a.format(double8) + " d";
		} else if (double6 > 0.5) {
			return a.format(double6) + " h";
		} else {
			return double4 > 0.5 ? a.format(double4) + " m" : double2 + " s";
		}
	};
}
