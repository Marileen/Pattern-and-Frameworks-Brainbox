
// Returns true if browser supports the crypto api
 export function supportsCrypto() {
  return window.crypto && crypto.subtle && window.TextEncoder;
};

// Hash function in javascript
export async function hash(algo, str) {
  return crypto.subtle.digest(algo, new TextEncoder().encode(str));
};

// Base64 encode
export async function encode64(buff) {
  return btoa(new Uint8Array(buff).reduce((s, b) => s + String.fromCharCode(b), ''));
}
