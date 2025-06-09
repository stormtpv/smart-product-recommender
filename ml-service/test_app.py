from fastapi.testclient import TestClient
from app.main import app  # убедись, что путь правильный: app.main или main и т.д.

client = TestClient(app)

def test_recommend_returns_recommendations():
    response = client.get("/recommend", params={"user_id": "123", "product_id": "456"})

    assert response.status_code == 200
    data = response.json()

    assert "recommendations" in data
    assert isinstance(data["recommendations"], list)
    assert len(data["recommendations"]) == 3
    assert all(isinstance(r, str) for r in data["recommendations"])
