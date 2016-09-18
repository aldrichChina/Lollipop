package com.jia.lollipop.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.Random;

/**
 * <pre>
 *     desc  : 设备相关工具类
 * </pre>
 */
public class DeviceUtils {

    private DeviceUtils() {
    }

    /**
     * 获取设备MAC地址
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>}</p>
     *
     * @param context 上下文
     * @return MAC地址
     */
    public static String getMacAddress(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        if (info != null) {
            String macAddress = info.getMacAddress();
            if (macAddress != null) {
                return macAddress.replace(":", "");
            }
        }
        return null;
    }

    /**
     * 获取设备MAC地址（指令方式）
     * <p/>
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>}</p>
     *
     * @return MAC地址
     */

    public static String getMacAddress() {
        String macAddress = null;
        LineNumberReader lnr = null;
        InputStreamReader isr = null;
        try {
            Process pp = Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address");
            isr = new InputStreamReader(pp.getInputStream());
            lnr = new LineNumberReader(isr);
            macAddress = lnr.readLine().replace(":", "");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            FileUtils.closeIO(lnr, isr);
        }
        return macAddress == null ? "" : macAddress;
    }

    /**
     * 获取设备厂商，如Xiaomi
     *
     * @return 设备厂商
     */
    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     * 获取设备型号，如MI2SC
     *
     * @return 设备型号
     */
    public static String getModel() {
        String model = Build.MODEL;
        if (model != null) {
            model = model.trim().replaceAll("\\s*", "");
        } else {
            model = "";
        }
        return model;
    }
    
    /**
	 * GSM手机的 IMEI 和 CDMA手机的 MEID.（保证16位 ，小米曾出现获取15位的情况）
	 */
    public static String getIMEI(Context context) {
		TelephonyManager mTm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		if (mTm!=null) {
			String imei = mTm.getDeviceId();
			if (imei != null) {
				if (imei.length() < 16 && imei.length() > 0) {
					while (imei.length() < 16) {
						imei += imei;
					}
					return imei.substring(0, 16);
				} else if (imei.length() <= 0) {
					return null;
				} else {
					return imei.substring(0, 16);
				}
			} else {
				return null;
			}
		}else {
			return null;
		}
	}
    
    /**
	 * 发票协同  秘钥生成  16位  先取Imei，else取mac，else随机数
	 */
    private static String sID = null;
    public synchronized static String getid(Context context) {
		if (sID == null) {
			sID = getIMEI(context);
			if (sID == null) {
				sID = getMacAddress(context);
				if (sID==null) {
					for (int i = 0; i < 4; i++) {
						sID += ""+(1000+new Random().nextInt(9000));
					}
				}
				return sID;
			}
		}
		return sID;
	}
    
    /**
	 * 检查sim卡状态
	 * 
	 * @param 
	 * @return
	 */
	public static boolean checkSimState(Context context) {
		TelephonyManager tm = (TelephonyManager)context
				.getSystemService(Context.TELEPHONY_SERVICE);
		if (tm.getSimState() == TelephonyManager.SIM_STATE_ABSENT
				|| tm.getSimState() == TelephonyManager.SIM_STATE_UNKNOWN) {
			return false;
		}

		return true;
	}
}