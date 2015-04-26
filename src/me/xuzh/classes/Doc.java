package me.xuzh.classes;

/**
 * Created by Xu on 4/18/15.
 */
public class Doc {
    private String I = "";
    private String U = "";
    private String S = "";
    private String M = "";
    private String T = "";
    private String P = "";
    private String W = "";
    private String A = "";

    public void checkEmpty() {
        checkEmpty("I", I);
        checkEmpty("U", U);
        checkEmpty("S", S);
        checkEmpty("M", M);
        checkEmpty("T", T);
        checkEmpty("P", P);
        checkEmpty("W", W);
        checkEmpty("A", A);
    }

    private void checkEmpty(String strName, String str) {
        if (str.equals("")) {
            System.out.println(strName + " is empty.");
        }
    }

    @Override
    public String toString() {
        return "Doc{" +
                "I='" + I + '\'' +
                ", U='" + U + '\'' +
                ", S='" + S + '\'' +
                ", M='" + M + '\'' +
                ", T='" + T + '\'' +
                ", P='" + P + '\'' +
                ", W='" + W + '\'' +
                ", A='" + A + '\'' +
                '}';
    }

    public String getI() {
        return I;
    }

    public void setI(String i) {
        I = i;
    }

    public String getU() {
        return U;
    }

    public void setU(String u) {
        U = u;
    }

    public String getS() {
        return S;
    }

    public void setS(String s) {
        S = s;
    }

    public String getM() {
        return M;
    }

    public void setM(String m) {
        M = m;
    }

    public String getT() {
        return T;
    }

    public void setT(String t) {
        T = t;
    }

    public String getP() {
        return P;
    }

    public void setP(String p) {
        P = p;
    }

    public String getW() {
        return W;
    }

    public void setW(String w) {
        W = w;
    }

    public String getA() {
        return A;
    }

    public void setA(String a) {
        A = a;
    }
}
