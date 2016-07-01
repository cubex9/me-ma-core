
package eu.cxn.mema.util;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;

/**
 * ruzne stringove utilitky
 *
 * @author kubasek
 */
public class Strings {

    /**
     * zjisti jestli je retezec prazdny
     */
    public static boolean isNullOrEmpty(String s) {
        if (s == null || "".equals(s.trim())) {
            return true;
        }
        return false;
    }

    /**
     * zjisti jestli je retezec prazdny vcetne ignorovani koncovek radku
     */
    public static boolean isNullOrEmptyLine(String s) {
        if (s == null || "".equals(s.replaceAll("^\\s+|\\n+", ""))) {
            return true;
        }
        return false;

    }

    /**
     *
     * @param separator
     * @param o
     * @return
     */
    public static String join(String separator, Object[] o) {
        String res = "";
        if (o != null) {
            for (Object s : o) {
                if (s == null) {
                    res += "".equals(res) ? "null" : (separator + "null");
                } else {
                    res += res.compareTo("") == 0 ? s.toString() : (separator + s.toString());
                }
            }
        }
        return res;
    }

    /**
     * 'streamovaci' pro StringBuilder
     */
    public static StringBuilder join(StringBuilder sb, String separator, Object[] o) {
        if (o != null) {
            for (int i = 0; i < o.length; i++) {
                if (i != 0) {
                    sb.append(separator);
                }
                sb.append(o[i] == null ? "null" : o[i].toString());
            }
        }
        return sb;
    }

    public static String join(String sepa, Iterable<Object> ike) {
        return join(sepa, ike, null);
    }

    /**
     * inner joiner based on iterator
     *
     * @param separator
     * @param ike
     */
    public static String join(String separator, Iterable<Object> ike, String format) {
        String res = "";
        if (ike != null) {
            for (Object s : ike) {
                if (format == null) {
                    res += "".equals(res) ? s : (separator + s);
                } else {
                    res += "".equals(res) ? String.format(format, s) : (separator + String.format(format, s));
                }
            }
        }
        return res;
    }

    /**
     * inner joiner based on iterator
     *
     * @param separator
     * @param ike
     */
    public static String join(String separator, Collection<String> ike, String format) {
        String res = "";
        if (ike != null) {
            for (String s : ike) {
                if (format == null) {
                    res += "".equals(res) ? s : (separator + s);
                } else {
                    res += "".equals(res) ? String.format(format, s) : (separator + String.format(format, s));
                }
            }
        }
        return res;
    }

    /**
     * pospojuje pole s nejakym sepatorkem od indexu, pocet jeli 0, az do konce
     *
     * @param separator
     * @param start
     * @param count
     * @param list
     * @return
     */
    public static String join(String separator, int start, int count, String... list) {
        String res = "";
        /**
         * small controls
         */
        if (start < 0 || count < 0 || start + count > list.length) {
            return res;
        }
        /**
         * goahead
         */
        if (list != null) {
            int end = count <= 0 ? list.length : start + count;
            for (int i = start; i < end; i++) {
                res += "".equals(res) ? list[i] : (separator + list[i]);
            }
        }
        return res;
    }

    /**
     * jednoduse pospojuje slova v listu pomoci nejakeho separatorku
     *
     * @param separator
     * @param list
     * @return
     */
    public static String join(String separator, String... list) {
        String res = "";
        if (list != null) {
            for (String s : list) {
                res += res.compareTo("") == 0 ? s : (separator + s);
            }
        }
        return res;
    }

    /**
     * zmerguje dva seznamy stringu, zjistuje jestli uz slovo ve druhem existuje, kdyz ano,
     * nepridavaho nejoblibenejsi spusob pouziti:
     * <pre>
     *  join( ",", mergeLi( one.split("\\,"), two.split("\\,")));
     * </pre>
     */
    public static Collection<String> mergeLi(String[] one, String[] two) {
        List<String> res = new ArrayList<>();
        for (String o : one) {
            res.add(o);
        }
        for (String t : two) {
            if (!res.contains(t)) {
                res.add(t);
            }
        }

        return res;
    }

    /**
     *
     * @param w
     * @param count
     * @return
     */
    public static String fill(String w, int count) {
        String res = "";
        for (int i = 0; i < count; i++) {
            res += w;
        }

        return res;
    }

    /**
     * udela split, stim ze ignoruje split charracter, kdyz je token v nejake sekci, pole sekci je
     * zadano start/end stringem, pro jistotu: split character je JEDNO PISMENKO, stejne tak section
     * start a end je kazdy JEDNO PISMENKO, dohromady tedy DVE ;-)
     *
     * example:
     * <pre>
     *   sectionSplit( "make(a1,a2),fake(a3,a1),..", ",", "()" ); *
     * </pre> zajisti rozparsrovani na jednotlive fce: make(a1,a2) atak dale..
     */
    public static List<String> sectionSplit(String data, String splitChars, String... sections) {

        List<String> result = new ArrayList<>();
        /* section queue */
        String sQ = "";
        /* vysledny token */
        String r = "";

        StringTokenizer st = new StringTokenizer(data, splitChars + Strings.join("", sections), true);
        while (st.hasMoreTokens()) {

            /**
             * skladame data nebo tokeny, az kdyz je rozpoznan jasne zacatek nebo konec bloku
             * ridiciho kodu, vola se callback do parseru s tokenem
             */
            int isq;
            String tN = st.nextToken();
            if (splitChars.equals(tN) && "".equals(sQ)) {
                result.add(r);
                r = "";
            } else if ((isq = sectionContains(tN, sections)) != -1) {
                /**
                 * neco nalezl.., startovni znak prida do fronty
                 */
                if (sections[isq].startsWith(tN)) {
                    sQ += tN;
                } else {
                    sQ = sQ.length() < 2 ? "" : sQ.substring(0, sQ.length() - 1);
                }
                r += tN;
            } else {
                r += tN;
            }
        }
        /**
         * poslednacka, kdyz neni empty..
         */
        if (!Strings.isNullOrEmpty(r)) {
            result.add(r);
        }
        return result;
    }

    /**
     * projede sections a kdyz v nekte vnich najde token, vrati cislo
     */
    protected static int sectionContains(String t, String... sections) {
        for (int i = 0; i < sections.length; i++) {
            if (sections[i].contains(t)) {
                return i;
            }
        }
        return -1;
    }

//    /**
//     * nacte stream do stringu, skratka pro JanStadler
//     */
//    public static String from(BufferedReader br) {
//        return Lq.grepS(br).toStringBuilder().toString();
//    }

    /**
     * prvni pismenko velky
     */
    public static String capitalize(String s) {
        if (isNullOrEmpty(s)) {
            return "";
        }

        if (s.length() > 1) {
            return s.substring(0, 1).toUpperCase() + s.substring(1);
        } else {
            return s.toUpperCase();
        }
    }

    /**
     * skratka pro string builder
     */
    public static StringBuilder sb() {
        return new StringBuilder();
    }
}
