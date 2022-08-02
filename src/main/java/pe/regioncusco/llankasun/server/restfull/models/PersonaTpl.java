package pe.regioncusco.llankasun.server.restfull.models;

import java.util.List;

public class PersonaTpl {
    private boolean success;
    private String msg;
    private List<PersonaData> data;
    private int total;

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

    public List<PersonaData> getData() {
        return data;
    }

    public void setData(List<PersonaData> data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
