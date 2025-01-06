

<Generating self-signed certificate>
keytool -genkeypair -alias springboot \
  -keyalg RSA -keysize 2048 -storetype PKCS12 \
  -keystore keystore.p12 -validity 3650 \
  -dname "CN=localhost, OU=IT, O=My Company Ltd, L=New York, ST=NY, C=US"



