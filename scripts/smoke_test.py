#!/usr/bin/env python3
"""Standard-library smoke test. Requires running Spring Boot on port 8081.

Run: python scripts/smoke_test.py
"""
import json
import urllib.error
import urllib.request

ROOT = "http://localhost:8081"

def request(method, path, payload=None):
    data = json.dumps(payload).encode("utf-8") if payload is not None else None
    req = urllib.request.Request(ROOT + path, data=data, method=method)
    if data is not None:
        req.add_header("Content-Type", "application/json")
    try:
        with urllib.request.urlopen(req, timeout=10) as response:
            body = response.read().decode("utf-8")
            return response.status, json.loads(body) if body else None
    except urllib.error.HTTPError as exc:
        body = exc.read().decode("utf-8")
        return exc.code, json.loads(body) if body else None

def main():
    code, created = request("POST", "/api/comptes", {
        "solde": 1000, "typeCompte": "COURANT", "devise": "MAD"
    })
    assert code == 201, (code, created)
    cid = created["id"]
    try:
        status, out = request("POST", f"/api/comptes/{cid}/depot", {"montant": 500})
        assert status == 200 and float(out["solde"]) == 1500.0, (status, out)
        status, out = request("POST", f"/api/comptes/{cid}/retrait", {"montant": 200})
        assert status == 200 and float(out["solde"]) == 1300.0, (status, out)
        status, err = request("POST", f"/api/comptes/{cid}/retrait", {"montant": 9999})
        assert status == 409, (status, err)
        status, listing = request("GET", "/api/comptes")
        assert status == 200 and any(c["id"] == cid for c in listing)
        status, schema = request("GET", "/v3/api-docs")
        assert status == 200 and "paths" in schema
        status, rest = request("GET", "/data-rest/comptes?projection=resume")
        assert status == 200 and "_embedded" in rest
        status, gql = request("POST", "/graphql", {"query": "{allComptes{id solde typeCompte}}"})
        assert status == 200 and gql.get("data", {}).get("allComptes") is not None, (status, gql)
        print("PASS: REST CRUD, service rules, Swagger, Data REST, GraphQL")
    finally:
        request("DELETE", f"/api/comptes/{cid}")

if __name__ == "__main__":
    main()
