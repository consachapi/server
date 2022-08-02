package pe.regioncusco.llankasun.server.enums;

import org.springframework.lang.Nullable;

public enum Device {
    WEB(1, "Web"),
    ANDROID(2, "Android"),
    IPHONE(3, "iPhone"),
    IPOD(4, "iPod"),
    IPAD(5, "iPad");

    private final Integer value;
    private final String key;
    private static final Device[] VALUES = values();

    Device(Integer value, String key) {
        this.value = value;
        this.key = key;
    }

    public Integer value(){
        return this.value;
    }

    public String key(){
        return this.key;
    }

    public static Device valueOf(Integer statusCode) {
        Device status = resolve(statusCode);
        if (status == null) {
            //throw new NotFoundException("No matching constant for [" + statusCode + "]");
            return null;
        } else {
            return status;
        }
    }

    @Nullable
    public static Device resolve(Integer statusCode) {
        Device[] var1 = VALUES;
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            Device status = var1[var3];
            if (status.value == statusCode) {
                return status;
            }
        }
        return null;
    }
}
