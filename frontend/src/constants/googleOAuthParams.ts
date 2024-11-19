const BASE_URI = 'https://accounts.google.com/o/oauth2/v2/auth';
const nonce = new Uint32Array(1);
crypto.getRandomValues(nonce);

const OAUTH_PARAMS = {
  client_id: import.meta.env.VITE_GOOGLE_CLIENT_ID,
  redirect_uri: import.meta.env.VITE_REDIRECT_URI,
  response_type: 'id_token',
  scope: 'openid profile email',
  nonce,
};
const GOOGLE_OAUTH_URL = `${BASE_URI}?${new URLSearchParams(Object.entries(OAUTH_PARAMS))}`;

export default GOOGLE_OAUTH_URL;
