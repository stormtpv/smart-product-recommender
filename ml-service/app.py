from fastapi import FastAPI, Query
from typing import List
from pydantic import BaseModel
import random

app = FastAPI()

class RecommendResponse(BaseModel):
    recommendations: List[str]

@app.get("/recommend", response_model=RecommendResponse)
def recommend(user_id: str = Query(...), product_id: str = Query(...)):
    return RecommendResponse(recommendations=[f"prod-{random.randint(100, 999)}" for _ in range(3)])