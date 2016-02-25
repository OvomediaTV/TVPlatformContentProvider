# TV Platform Content Provider

---

## 如何整合內容到首頁電視框 (傳送 Playlist 給 OVO Launcher)

請參考 ovobox-tv-platform-content-provider-sample 並編譯安裝至 OVO 電視盒。

選取首頁的電視框並長按遙控器 `OK 鍵` 在畫面中會彈出對話框，並且會有 `PlaylistProviderDemo` 的選項。

目前 `Palylist Provider` 是綁定 `Package Name` 和 `Signature Hash Code`，如果想測試鎖開發的程式是否正常運作, 請告知我們的工程人員 `Package Name` 及 `Signature Hash Code` ，聯絡信箱 `feedback@ovomedia.tv`。

取得 `Signature Hash Code` 的方式如下, 請給我下面程式碼產生的結果：

~~~
try {
    Signature[] sigs;
    sigs = getPackageManager().getPackageInfo("com.twgtablet.android", PackageManager.GET_SIGNATURES).signatures;
    for (Signature sig : sigs) {
        System.out.println("Signature hashcode : " + Integer.toHexString(sig.hashCode()));
    }
} catch (NameNotFoundException e) {
    e.printStackTrace();
}
~~~

