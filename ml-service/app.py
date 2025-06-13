from fastapi import FastAPI, Query
from typing import List
from pydantic import BaseModel
from prometheus_client import Counter, generate_latest
from fastapi.responses import Response
import random

app = FastAPI()

REQUEST_COUNT = Counter('request_count', 'Total request count')

class RecommendResponse(BaseModel):
    recommendations: List[str]

@app.get("/")
async def root():
    REQUEST_COUNT.inc()
    return {"Hello": "World"}

@app.get("/metrics")
async def metrics():
    return Response(generate_latest(), media_type="text/plain")

@app.get("/recommend", response_model=RecommendResponse)
def recommend(user_id: str = Query(...), product_id: str = Query(...)):
    return RecommendResponse(recommendations=[f"prod-{random.randint(100, 999)}" for _ in range(3)])