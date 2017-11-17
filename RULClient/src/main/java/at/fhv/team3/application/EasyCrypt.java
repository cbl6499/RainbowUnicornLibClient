package at.fhv.team3.application;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;

/**
 * @author Alexander Gr
 * @see "http://blog.axxg.de"
 *
 * EasyCrypt wurd nicht von uns selber implementiert
 * "https://blog.axxg.de/java-verschluesselung-beispiel-quickstart/"
 *
 */
public class EasyCrypt {

    private Key key = null;
    private String verfahren = null;

    /**
     * @param "Key" verwendeter Schluessel
     * @param verfahren bestimmt das verwendete Verschluesselungsverfahren "RSA", "AES", ....
     * @throws Exception
     */
    public EasyCrypt(Key k, String verfahren) throws Exception {
        this.key = k;
        this.verfahren = verfahren;
    }

    /** Verschluesselt einen Text in BASE64
     * @param text Klartext
     * @return BASE64 String
     * @throws Exception
     */
    public String encrypt(String text) throws Exception {
        // integritaet pruefen
        valid();

        // Verschluesseln
        Cipher cipher = Cipher.getInstance(verfahren);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encrypted = cipher.doFinal(text.getBytes());

        // bytes zu Base64-String konvertieren
        BASE64Encoder myEncoder = new BASE64Encoder();
        String geheim = myEncoder.encode(encrypted);

        return geheim;
    }

    /** Entschluesselt einen BASE64 kodierten Text
     * @param geheim BASE64 kodierter Text
     * @return Klartext
     * @throws Exception
     */
    public String decrypt(String geheim) throws Exception {
        // integritaet pruefen
        valid();

        // BASE64 String zu Byte-Array
        BASE64Decoder myDecoder = new BASE64Decoder();
        byte[] crypted = myDecoder.decodeBuffer(geheim);

        // entschluesseln
        Cipher cipher = Cipher.getInstance(verfahren);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] cipherData = cipher.doFinal(crypted);
        return new String(cipherData);
    }

    //++++++++++++++++++++++++++++++
    // Validierung
    //++++++++++++++++++++++++++++++

    private boolean valid() throws Exception{
        if(verfahren == null){
            throw new NullPointerException("Kein Verfahren angegeben!");
        }

        if(key == null){
            throw new NullPointerException("Keinen Key angegeben!");
        }

        if(verfahren.trim() == ""){
            throw new NullPointerException("Kein Verfahren angegeben!");
        }
        return true;
    }

    //++++++++++++++++++++++++++++++
    // Getter und Setter
    //++++++++++++++++++++++++++++++

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public String getVerfahren() {
        return verfahren;
    }

    public void setVerfahren(String verfahren) {
        this.verfahren = verfahren;
    }
}
