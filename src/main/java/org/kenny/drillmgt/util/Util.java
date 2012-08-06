package org.kenny.drillmgt.util;

public class Util {

	public static int maxPageSize(int count, int pageSize) {
		if (pageSize > 0) {
			if ((count % pageSize) != 0) {
				return (count / pageSize) + 1;
			} else {
				return (count / pageSize);
			}
		}
		return 0;
	}
}
