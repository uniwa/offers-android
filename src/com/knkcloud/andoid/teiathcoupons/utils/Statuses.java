package com.knkcloud.andoid.teiathcoupons.utils;

/**
 * Represents the statuses of server Response
 * 
 * @author Karpouzis Koutsourakis Dinopoulos
 * 
 */
public final class Statuses {

	public static final class Internet {
		public static final AStatus SUCCESS = new AStatus(200, "Internet-Ok");
		public static final AStatus BAD_REQUEST = new AStatus(400,
				"Internet-BadRequest");
		public static final AStatus UNAUTHORIZED = new AStatus(401,
				"Internet-Unauthorized");
		public static final AStatus FORBIDDEN = new AStatus(403,
				"Internet-Forbidden");
		public static final AStatus SERVERERROR = new AStatus(500,
				"Internet-ServerAStatus");
		public static final AStatus TIMEOUT = new AStatus(408,
				"Internet-TimeOut");
		public static final AStatus UNDEFINED = new AStatus(555,
				"Internet-Undefined");
	}

	public static final class Generic {
		public static final AStatus SUCCESS = new AStatus(0, "Generic-Success");
		public static final AStatus FAIL = new AStatus(1, "Generic-Fail");
		public static final AStatus UNDEFINED = new AStatus(-1,
				"Generic-Undefined");
	}

	public static final class Misc {
		public static final AStatus PARAMETER_NUM = new AStatus(100,
				"Methods-ParameterNum");
		public static final AStatus JSON_ERROR = new AStatus(101,
				"Methods-JsonError");
	}

}
