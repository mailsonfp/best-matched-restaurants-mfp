import React from "react";

function TokenDisplay({ token }) {
  const copyToken = () => {
    navigator.clipboard.writeText(token).then(() => {
      alert('Token copied to clipboard!');
    }).catch(err => {
      console.error('Failed to copy: ', err);
      alert('Failed to copy token. Please copy manually.');
    });
  };

  return (
    <div className="token-display">
      <h2>Token Gerado</h2>
      {token ? (
        <>
          <p className="token-text">{token}</p>
          <button className="btn btn-secondary me-2" onClick={copyToken}>Copy Token</button>
        </>
      ) : (
        <p>Nenhum token gerado ainda.</p>
      )}
    </div>
  );
}

export default TokenDisplay;