package com.runecore.util;

import java.util.Random;

import com.runecore.network.io.Message;

/**
 * Misc.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 12, 2013
 */
public class Misc {
    
    private static final Random RANDOM = new Random();
    
    public static int random() {
	return RANDOM.nextInt();
    }
    
    public static int random(int max) {
	return RANDOM.nextInt(max);
    }
    
    public static int random(int min, int max) {
	return min + (max == min ? 0 : RANDOM.nextInt(max - min));
    }
    
    /**
     * Format a player's name for display
     * @param name
     * @return
     */
    public static String capitalize(String name) {
	name = name.replaceAll("_", " ");
	name = name.toLowerCase();
	StringBuilder newName = new StringBuilder();
	boolean wasSpace = true;
	for (int i = 0; i < name.length(); i++) {
	    if (wasSpace) {
		newName.append(("" + name.charAt(i)).toUpperCase());
		wasSpace = false;
	    } else {
		newName.append(name.charAt(i));
	    }
	    if (name.charAt(i) == ' ') {
		wasSpace = true;
	    }
	}
	return newName.toString();
    }
    
    public static int[] anIntArray241 = { 215, 203, 83, 158, 104, 101, 93, 84,
	107, 103, 109, 95, 94, 98, 89, 86, 70, 41, 32, 27, 24, 23, -1, -2,
	26, -3, -4, 31, 30, -5, -6, -7, 37, 38, 36, -8, -9, -10, 40, -11,
	-12, 55, 48, 46, 47, -13, -14, -15, 52, 51, -16, -17, 54, -18, -19,
	63, 60, 59, -20, -21, 62, -22, -23, 67, 66, -24, -25, 69, -26, -27,
	199, 132, 80, 77, 76, -28, -29, 79, -30, -31, 87, 85, -32, -33,
	-34, -35, -36, 197, -37, 91, -38, 134, -39, -40, -41, 97, -42, -43,
	133, 106, -44, 117, -45, -46, 139, -47, -48, 110, -49, -50, 114,
	113, -51, -52, 116, -53, -54, 135, 138, 136, 129, 125, 124, -55,
	-56, 130, 128, -57, -58, -59, 183, -60, -61, -62, -63, -64, 148,
	-65, -66, 153, 149, 145, 144, -67, -68, 147, -69, -70, -71, 152,
	154, -72, -73, -74, 157, 171, -75, -76, 207, 184, 174, 167, 166,
	165, -77, -78, -79, 172, 170, -80, -81, -82, 178, -83, 177, 182,
	-84, -85, 187, 181, -86, -87, -88, -89, 206, 221, -90, 189, -91,
	198, 254, 262, 195, 196, -92, -93, -94, -95, -96, 252, 255, 250,
	-97, 211, 209, -98, -99, 212, -100, 213, -101, -102, -103, 224,
	-104, 232, 227, 220, 226, -105, -106, 246, 236, -107, 243, -108,
	-109, 231, 237, 235, -110, -111, 239, 238, -112, -113, -114, -115,
	-116, 241, -117, 244, -118, -119, 248, -120, 249, -121, -122, -123,
	253, -124, -125, -126, -127, 259, 258, -128, -129, 261, -130, -131,
	390, 327, 296, 281, 274, 271, 270, -132, -133, 273, -134, -135,
	278, 277, -136, -137, 280, -138, -139, 289, 286, 285, -140, -141,
	288, -142, -143, 293, 292, -144, -145, 295, -146, -147, 312, 305,
	302, 301, -148, -149, 304, -150, -151, 309, 308, -152, -153, 311,
	-154, -155, 320, 317, 316, -156, -157, 319, -158, -159, 324, 323,
	-160, -161, 326, -162, -163, 359, 344, 337, 334, 333, -164, -165,
	336, -166, -167, 341, 340, -168, -169, 343, -170, -171, 352, 349,
	348, -172, -173, 351, -174, -175, 356, 355, -176, -177, 358, -178,
	-179, 375, 368, 365, 364, -180, -181, 367, -182, -183, 372, 371,
	-184, -185, 374, -186, -187, 383, 380, 379, -188, -189, 382, -190,
	-191, 387, 386, -192, -193, 389, -194, -195, 454, 423, 408, 401,
	398, 397, -196, -197, 400, -198, -199, 405, 404, -200, -201, 407,
	-202, -203, 416, 413, 412, -204, -205, 415, -206, -207, 420, 419,
	-208, -209, 422, -210, -211, 439, 432, 429, 428, -212, -213, 431,
	-214, -215, 436, 435, -216, -217, 438, -218, -219, 447, 444, 443,
	-220, -221, 446, -222, -223, 451, 450, -224, -225, 453, -226, -227,
	486, 471, 464, 461, 460, -228, -229, 463, -230, -231, 468, 467,
	-232, -233, 470, -234, -235, 479, 476, 475, -236, -237, 478, -238,
	-239, 483, 482, -240, -241, 485, -242, -243, 499, 495, 492, 491,
	-244, -245, 494, -246, -247, 497, -248, 502, -249, 506, 503, -250,
	-251, 505, -252, -253, 508, -254, 510, -255, -256, 0 
    };


    public static String decryptPlayerChat(Message str, int totalChars) {
	try {
	    if (totalChars == 0) {
		return "";
	    }
	    int charsDecoded = 0;
	    int i_4_ = 0;
	    String s = "";
	    for (;;) {
		byte i_7_ = (byte) str.readByte();
		if (i_7_ >= 0) {
		    i_4_++;
		} else {
		    i_4_ = anIntArray241[i_4_];
		}
		int i_8_;
		if ((i_8_ = anIntArray241[i_4_]) < 0) {
		    s += (char) (byte) (i_8_ ^ 0xffffffff);
		    if (totalChars <= ++charsDecoded) {
			break;
		    }
		    i_4_ = 0;
		}
		if (((i_7_ & 0x40) ^ 0xffffffff) != -1) {
		    i_4_ = anIntArray241[i_4_];
		} else {
		    i_4_++;
		}
		if ((i_8_ = anIntArray241[i_4_]) < 0) {
		    s += (char) (byte) (i_8_ ^ 0xffffffff);
		    if (++charsDecoded >= totalChars) {
			break;
		    }
		    i_4_ = 0;
		}
		if ((0x20 & i_7_) == 0) {
		    i_4_++;
		} else {
		    i_4_ = anIntArray241[i_4_];
		}
		if ((i_8_ = anIntArray241[i_4_]) < 0) {
		    s += (char) (byte) (i_8_ ^ 0xffffffff);
		    if (totalChars <= ++charsDecoded) {
			break;
		    }
		    i_4_ = 0;
		}
		if (((0x10 & i_7_) ^ 0xffffffff) == -1) {
		    i_4_++;
		} else {
		    i_4_ = anIntArray241[i_4_];
		}
		if ((i_8_ = anIntArray241[i_4_]) < 0) {
		    s += (char) (byte) (i_8_ ^ 0xffffffff);
		    if (totalChars <= ++charsDecoded) {
			break;
		    }

		    i_4_ = 0;
		}
		if (((0x8 & i_7_) ^ 0xffffffff) != -1) {
		    i_4_ = anIntArray241[i_4_];
		} else {
		    i_4_++;
		}
		if ((i_8_ = anIntArray241[i_4_]) < 0) {
		    s += (char) (byte) (i_8_ ^ 0xffffffff);
		    if (++charsDecoded >= totalChars) {
			break;
		    }
		    i_4_ = 0;
		}
		if ((0x4 & i_7_) == 0) {
		    i_4_++;
		} else {
		    i_4_ = anIntArray241[i_4_];
		}
		if ((i_8_ = anIntArray241[i_4_]) < 0) {
		    s += (char) (byte) (i_8_ ^ 0xffffffff);
		    if (totalChars <= ++charsDecoded) {
			break;
		    }
		    i_4_ = 0;
		}
		if (((i_7_ & 0x2) ^ 0xffffffff) != -1) {
		    i_4_ = anIntArray241[i_4_];
		} else {
		    i_4_++;
		}
		if ((i_8_ = anIntArray241[i_4_]) < 0) {
		    s += (char) (byte) (i_8_ ^ 0xffffffff);
		    if (totalChars <= ++charsDecoded) {
			break;
		    }
		    i_4_ = 0;
		}
		if (((i_7_ & 0x1) ^ 0xffffffff) != -1) {
		    i_4_ = anIntArray241[i_4_];
		} else {
		    i_4_++;
		}
		if ((i_8_ = anIntArray241[i_4_]) < 0) {
		    s += (char) (byte) (i_8_ ^ 0xffffffff);
		    if (++charsDecoded >= totalChars) {
			break;
		    }
		    i_4_ = 0;
		}
	    }
	    return s;
	} catch (RuntimeException runtimeexception) {
	}
	return "";
    }
    

}
