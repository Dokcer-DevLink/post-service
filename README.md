<div align="center">
<h1>Post-Service</h1>
<h3> 멘토와 멘티 구인을 위한 게시글 서비스 

포스트 서비스</h3>
</div>

<br>
<br>


## Architecture
<img width="9116" alt="아키텍처" src="https://github.com/Dokcer-DevLink/post-service/assets/80077569/23693dfa-779a-4d3f-8ccd-9fa34790ace4">


<br>
<br>

## Description

### - 포스트(게시글) CRUD 구현

### - 포스트 이미지 S3 버킷에 업로드
Base64 방식으로 인코딩 된 이미지 데이터를 디코딩하여 S3 버킷에 업로드 한 뒤, 이미지url을 DB에 저장하여 게시글 조회시 클라이언트가 이미지에 접근 가능하도록 구현하였다. 

### - 하버사인(Haversine) 공식으로 포스트 추천하기
자동매칭시, 자동매칭 모달에 입력된 장소와 가장 가까운 포스트 리스트를 조회하는 기능을 구현하였다. 위도, 경도 좌표 사이의 최단거리를 구하는 하버사인(Haversine) 공식을 QueryDSL로 구현하여 OrderBy 조건문을 만들어 입력된 장소와 가장 가까운 포스트 리스트를 조회하였다.  

### - QueryDSL 사용
간단한 쿼리는 Spring Data JPA로 자동생성하였고 복잡한 쿼리는 타입 안정성과 동적 쿼리 생성을 고려하여 QueryDSL를 사용하였다.

<br>
<br>

