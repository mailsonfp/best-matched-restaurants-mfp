import React from "react";

function TokenDisplay({ token }) {
  return (
    <div className="token-display">
      <h2>Token Gerado</h2>
      {token ? (
        <p className="token-text">{token}</p>
      ) : (
        <p>Nenhum token gerado ainda.</p>
      )}
    </div>
  );
}

export default TokenDisplay;