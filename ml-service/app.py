from fastapi import FastAPI, Query
from prometheus_fastapi_instrumentator import Instrumentator
from typing import List
from pydantic import BaseModel
from prometheus_client import Counter, generate_latest
from fastapi.responses import Response
import random

app = FastAPI()

Instrumentator().instrument(app).expose(app)

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
    REQUEST_COUNT.inc()
    return RecommendResponse(recommendations=[f"prod-{random.randint(100, 999)}" for _ in range(3)])