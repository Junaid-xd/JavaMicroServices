    package com.example.User.Service.Utils;

    import io.jsonwebtoken.Claims;
    import io.jsonwebtoken.JwtParser;
    import io.jsonwebtoken.Jwts;
    import io.jsonwebtoken.security.Keys;
    import org.springframework.stereotype.Component;

    import javax.crypto.KeyGenerator;
    import javax.crypto.SecretKey;
    import java.security.Key;
    import java.security.NoSuchAlgorithmException;
    import java.util.Base64;
    import java.util.Date;
    import java.util.HashMap;
    import java.util.Map;

    @Component
    public class JwtUtil {
        private String secretKey = "";

        public static final String TOKEN_NAME = "myToken";

        public final long EXPIRATION_TIME = 24 * 3600000;

        public JwtUtil(){
            try {
                KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
                SecretKey sk = keyGen.generateKey();
                secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }


        public String generateToken(String email, String password) {
            Map<String, Object> claim = new HashMap<>();

            return Jwts.builder()
                    .subject(email)
                    .claims()
                    .add(claim)
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10))
                    .and()
                    .signWith(getKey())
                    .compact();
        }

        public Claims validateToken(String token) {
            JwtParser parser = Jwts.parser()
                    .setSigningKey(secretKey)
                    .build();

            try {
                return parser.parseSignedClaims(token).getPayload();
            }
            catch (Exception e) {
                return null;
            }
        }

        public String getEmailFromToken(String token) {
            Claims claims = validateToken(token);
            return claims != null ? claims.getSubject() : null;
        }

        public String getPasswordFromToken(String token) {
            Claims claims = validateToken(token);
            return claims != null ? (String) claims.get("password") : null;
        }

        public boolean isTokenExpired(String token) {
            Claims claims = validateToken(token);
            return claims != null && claims.getExpiration().before(new Date());
        }

        public long getExpirationTime() {
            return EXPIRATION_TIME;
        }

        public Key getKey(){
            byte[] keyBytes = Base64.getDecoder().decode(secretKey);
            return Keys.hmacShaKeyFor(keyBytes);
        }
    }
