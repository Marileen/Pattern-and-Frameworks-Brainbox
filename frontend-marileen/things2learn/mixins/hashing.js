
// define a mixin object
export default{
  methods: {
    // Returns if browser supports the crypto api
    supportsCrypto() {
      return window.crypto && crypto.subtle && window.TextEncoder;
    },

    // Hash function in javascript
    async hash(algo, str) {
      return crypto.subtle.digest(algo, new TextEncoder().encode(str));
    },

    // Base64 encode
    async encode64(buff) {
      return btoa(new Uint8Array(buff).reduce((s, b) => s + String.fromCharCode(b), ''));
    },
  }
}
