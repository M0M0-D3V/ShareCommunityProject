import React from "react";
import { Router } from "@reach/router";
import LogReg from "./views/LogReg.jsx";
import { Container } from "react-bootstrap";

function App() {
  return (
    <>
      <div>
        <Container>
          <Router>
            <LogReg path="/login" />
          </Router>
        </Container>
      </div>
    </>
  );
}

export default App;
