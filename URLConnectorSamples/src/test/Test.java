package test;

public class Test {

    public static void main(String... args) throws Exception {
        String czech = "Český";
        String japanese = "日本語";
        String czechUnicode = "\u06c4\u068C\u0665\u0673\u066B\u06C3\u06BD";
        String japaneseUnicode = "\u00E6\u0097\u00A5\u00E6\u009C\u00AC\u00E8\u00AA\u009E";
        String chinese = "\u00E6\u00B8\u00B8";
        
        
        
        String test2 = "\uFB20\uFB21\uFB22\uFB23\uFB24\uFB25\uFB26\uFB27\uFB28\uFB29\uFB2A\uFB2B\uFB2C\uFB2D\uFB2E\uFB2F";
        String test3 = "\uFB30\uFB31\uFB32\uFB33\uFB34\uFB35\uFB36\uFB37\uFB38\uFB39\uFB3A\uFB3B\uFB3C\uFB3D\uFB3E\uFB3F";
        System.out.println("czechUnicode:"+czechUnicode);
        //System.out.println("japaneseUnicode:"+japaneseUnicode);
        System.out.println("test:"+test2);
        System.out.println("test:"+test3);

        byte[] b = czech.getBytes("UTF-8");
        for (int i = 0; i < b.length; i++)
		{
			System.out.println(b[i]);
		}
        System.out.println(bytesToHex(b));
        
		System.out.println();
        b = japanese.getBytes("UTF-8");
        for (int i = 0; i < b.length; i++)
		{
			System.out.println(b[i]);
		}
        System.out.println(bytesToHex(b));
        System.out.println("UTF-8 czech: " + new String(czech.getBytes("UTF-8")));
        System.out.println("UTF-8 japanese: " + new String(japanese.getBytes("UTF-8")));

        System.out.println("ISO-8859-1 czech: " + new String(czech.getBytes("ISO-8859-1")));
        System.out.println("ISO-8859-1 japanese: " + new String(japanese.getBytes("ISO-8859-1")));
    }

    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
    
    public static void dumpString(String text) {
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            System.out.printf("%c - %04x\n", c, (int) c);
        }
    }
}