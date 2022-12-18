package com.akbarrahmatm.a2112500851_akbarrahmatm_apicrud;

public class Configuration {
    public static final String BASE_URL = "https://p11.api.akbarrahmatm.my.id/";
    public static final String URL_ADD = BASE_URL + "add.php";
    public static final String URL_GET_MHS = BASE_URL + "detail.php?id=";
    public static final String URL_GET_ALL = BASE_URL + "read.php";
    public static final String URL_UPDATE_MHS = BASE_URL + "update.php";
    public static final String URL_DELETE_MHS = BASE_URL + "delete.php?id=";

    //Request to PHP
    public static final String KEY_MHS_ID = "id";
    public static final String KEY_MHS_NAMA = "nama";
    public static final String KEY_MHS_ALAMAT = "alamat";
    public static final String KEY_MHS_jurusan = "jurusan";

    //JSON Tags
    public static final String TAG_JSON_ARRAY = "result";
    public static final String TAG_ID = "id";
    public static final String TAG_NAMA = "nama";
    public static final String TAG_ALAMAT = "alamat";
    public static final String TAG_JURUSAN = "jurusan";

    //ID mahasiswa
    public static final String MHS_ID = "mhs_id";
}
