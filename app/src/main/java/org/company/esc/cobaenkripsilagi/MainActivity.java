package org.company.esc.cobaenkripsilagi;import android.provider.ContactsContract;import android.support.v7.app.AppCompatActivity;import android.os.Bundle;import android.util.Base64;import android.view.View;import android.widget.Button;import android.widget.EditText;import android.widget.TextView;import android.widget.Toast;import java.security.MessageDigest;import javax.crypto.Cipher;import javax.crypto.spec.SecretKeySpec;public class MainActivity extends AppCompatActivity {    EditText inputText, inputPassword;    TextView outputText;    Button encBtn, decBtn;    String outputString;    String AES = "AES";    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(R.layout.activity_main);        inputText = (EditText) findViewById(R.id.inputtext);        inputPassword = (EditText) findViewById(R.id.inputPassword);        outputText = (TextView) findViewById(R.id.output);        encBtn = (Button) findViewById(R.id.encBtn);        decBtn = (Button) findViewById(R.id.decBtn);        encBtn.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                try {                    outputString = encrypt(inputText.getText().toString(), inputPassword.getText().toString());                    outputText.setText(outputString);                } catch (Exception e) {                    e.printStackTrace();                }            }        });        decBtn.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                try {                    outputString = decrypt (outputString, inputPassword.getText().toString());                    outputText.setText(outputString);                } catch (Exception e){                    Toast.makeText(MainActivity.this, "password salah !!!!!!", Toast.LENGTH_SHORT).show();                    e.printStackTrace();                }            }        });    }    private String decrypt(String outputString, String password) throws Exception {        SecretKeySpec key = generateKey(password);        Cipher c = Cipher.getInstance(AES);        c.init(Cipher.ENCRYPT_MODE, key);        byte [] decodeValue = Base64.decode(outputString, Base64.DEFAULT);        byte [] decValue = c.doFinal(decodeValue);        String decryptedValue = new String(decValue);        return decryptedValue;    }    private String encrypt(String data, String password) throws Exception {        SecretKeySpec key = generateKey (password);        Cipher c = Cipher.getInstance(AES);        c.init(Cipher.ENCRYPT_MODE, key);       byte[] encVal = c.doFinal(data.getBytes());       String encryptedValue = Base64.encodeToString(encVal, Base64.DEFAULT);       return encryptedValue;    }    private SecretKeySpec generateKey(String password) throws Exception {        final MessageDigest digest = MessageDigest.getInstance("SHA-256");        byte [] bytes = password.getBytes("UTF-8");        digest.update(bytes, 0, bytes.length);        byte [] key = digest.digest();        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");        return secretKeySpec;    }}