"use client";

import React from 'react';

const ClientButton: React.FC<{ onClick: () => void; children: React.ReactNode }> = ({ onClick, children }) => {
  return (
    <button onClick={onClick}>
      {children}
    </button>
  );
};

export default ClientButton;