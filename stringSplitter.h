#pragma once

#ifndef _StringSplitter_h
#define _StringSplitter_h

int splitByCharDouble(String str, char separator, float *out1, float *out2);
int checkStringSeparator(String str, char separator);

//======================================FUNCTIONS===============================================

int splitByCharDouble(String str, char separator, float *out1, float *out2) {
	int strErr = 0;

	strErr = checkStringSeparator(str, separator);

	if (strErr == -1) { return -1; }
	else if (strErr == 0) {
		String str1 = str.substring(0, str.indexOf(separator));
		String str2 = str.substring(str.indexOf(separator) + 1, str.length());
    
    //Serial.println("-" + str);
    //Serial.println("--" + str1);
    //Serial.println("--" + str2);

		*out1 = str1.toFloat();
		*out2 = str2.toFloat();

		return 0;
	}
	else { return -1; }
}



int checkStringSeparator(String str, char separator) {
	if (str.indexOf(separator) == -1) {
		return -1;
	}
	else {
		return 0;
	}
}

#endif

