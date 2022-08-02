package pe.regioncusco.llankasun.server.commons;

import java.util.ArrayList;
import java.util.List;

public class Tpl {
    private boolean success;
    private String msg;
    private List<?> data;
    private int total;

    public Tpl(boolean success, List<?> data, int total) {
        this.success = success;
        this.msg = "";
        this.data = data;
        this.total = total;
    }

    public Tpl(boolean success, String msg, List<?> data, int total) {
        this.success = success;
        this.msg = msg;
        this.data = data;
        this.total = total;
    }

    public Tpl(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
        this.data = new ArrayList<>();
        this.total = 0;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
