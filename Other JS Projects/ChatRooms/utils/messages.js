
function formatMessage(username, text) {
  return {
    username,
    text
  };
}
function formatAnnoucement(text) {
  return {
    text
  };
}

module.exports = {formatMessage, formatAnnoucement};
