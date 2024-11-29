package comp;

import java.util.Objects;

public class LogToken {
    private final int token;
    private final String prod;
    private final int linhaIndex;


    public LogToken(int token, String prod, int linhaIndex) {
        this.token = token;
        this.prod = prod;
        this.linhaIndex = linhaIndex;
    }

    public int getToken() {
        return token;
    }

    public String getProd() {
        return prod;
    }

    public int getLinhaIndex() {
        return linhaIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogToken logToken = (LogToken) o;
        return token == logToken.token;
    }

    @Override
    public int hashCode() {
        return Objects.hash(token);
    }
}
