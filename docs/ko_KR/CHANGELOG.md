# 업데이트 로그

## 4.8.0-beta1
2026-10-29
- 변경
  - 제작자 이름 변경됨
    - 패키지 변경: kr.hahaha98757 -> kr.runanoise
    - 새 저장소 URL 반영
    - 하위호환: 기존 패키지의 FileRemover 유지
  - 컨피그 개선: 관련 메시지 추가.
  - Player Visibility
    - Semi PV가 불을 반투명하게 렌더링함.
    - 플레이어 레이어가 겹쳐서 렌더링되지 않음.
  - Auto Splits: 명령어 수정 설정 추가.
  - Not Last: 활성화 시간 변경 설정 추가.
  - Auto Rejoin: 재접속 시도 시간 변경 설정 추가.
  - Recorder
    - 여러 설정 추가.
    - 출력 텍스트 수정.
    - 7일 이상 지난 .seg 파일을 삭제함.
- 버그 수정
  - Auto Splits: 포트 기본값 수정.
  - Recorder
    - 헬리콥터 탈출 승리 시 Recorder가 잘못된 기록을 저장함.
    - 헬리콥터 호출 기록을 승리 시 저장하도록 수정.
- 코드 수정
  - Mixin 리팩토링
  - Enums 수정


## 4.8.7
2026-09-24
- 추가: Patcher
  - 소총 소리가 들리지 않는 문제 수정.
- 제거: SST 한글 패치

## 4.8.6
2026-09-23
- 수정: FileRemover
  - 메인 함수 존재 확인
- 코드 수정: 리팩토링

## 4.8.5
2026-07-24
- 버그 수정: ZSV가 더 이상 동작하지 않음
- 코드 수정
  - 일부 객체를 바로 접근 가능하도록 수정
  - 모듈 정리 및 일부 class를 object로 변경
  - 컨피그 코드 수정

## 4.8.4
2026-06-21
- 코드 수정
  - Gradle 업데이트 롤백: 8.14.4 -> 8.8

## 4.8.3
2026-06-02
- 추가
  - 스피드런 모드
  - Internal Timer: 텍스트 스타일 옵션
- 제거: blockUnlegitMods, detectUnlegitMods 옵션
- 변경: Wave Delays
  - 텍스트 스타일 옵션
  - Wrath Mode 추가.
- 버그 수정
  - 게임 시작 시 RoundStartEvent가 발생하지 않음
  - 게임 시작 시 즉시 게임이 제거됨.
- 코드 수정
  - Kotlin 업데이트: 2.2.20 - > 2.3.21
  - Gradle 업데이트: 8.8 - > 8.14.4

## 4.8.2
2026-05-06
- 버그 수정: 타이머 보정
  - 연습 서버에서 타이머 보정이 취소되지 않음.

## 4.8.1
2026-05-06
- 변경: Zombies Utils 설정
  - 호환되는 권장 버전: 1.4.2 -> 1.4.4
  - Zombies Utils 한글 패치 제거.

## 4.8.0
2026-05-05
- 수정: Last Weapons
  - 아라비아 숫자로 된 레벨을 인식하지 않음: 연습 서버의 수정 사항 반영.

## 4.8.0-rc1
2026-05-04
- 변경: 연습 서버 호환성
  - Recorder가 기록을 저장하지 않음.
  - 난이도 감지를 옛 방식으로 사용.
  - Last Weapons가 아라비아 숫자로 된 레벨을 인식함.
  - 정보가 없는 라운드에서 모듈이 작동하지 않음.
  - 로비에서 스코어보드 정보를 제거함.


## 4.7.4
2026-05-03
- 변경
  - 호환되는 Zombies Utils 버전: 1.3.7 -> 1.4.2
  - Internal Timer: Server 모드가 쓰러짐, 사망 상태에서도 유지됨.
- 수정: Better Zombies Left
  - 남은 좀비 라인을 점수가 아닌 문자열 포함 여부로 감지.
  - 4.7.2로 롤백.
- 버그 수정
  - Powerup Patterns: 타이머가 0 이하로 진행됨.
  - 이미 존재하는 게임의 서버 번호로 새 게임을 시작하려는 경우 기존 게임 제거.

## 4.7.3
2026-04-26
- 변경: Better Zombies Left
  - Feather Client 등 스코어보드 관련 모드에서도 작동함.
- 버그 수정: Internal Timer
  - Server 모드에서 게임 오버 시 항상 Client 모드를 사용하는 버그 수정.
- 코드 수정: WaveDelays.playSound() 호출 조건

## 4.7.2
2026-04-10
- 수정: 번역 문자열
  - 자동 업데이트의 윈도우 전용 텍스트 제거.

## 4.7.1
2026-04-10
- 변경: 자동 업데이트를 다른 OS에서 사용할 수 있음.
- 코드 수정: 의미없는 super.initGui() 제거.

## 4.7.0
2026-03-04
- 수정사항 없음.

## 4.7.0-rc1
2026-03-02
- 추가: PRR 몹 스폰 데이터.
- 변경: Recorder
  - 타이머 보정 실패 시 라운드 1 구간 기록, 전체 기록을 측정하지 않음.

## 4.7.0-beta2
2026-02-26
- 버그 수정: Internal Timer
  - Client 모드에서 Wave Delays의 소리 재생이 2번 재생되는 버그.
  - 인게임 중 모드 변경 시 타이머가 잘못된 값을 가지는 버그.

## 4.7.0-beta1
2026-02-25
- 수정: Internal Timer
  - 컨피그 옵션 추가: 타이머 계산의 기준을 클라이언트와 서버 중 선택할 수 있도록 수정.
    - 클라이언트: 기존 방식. 클라이언트 TPS를 기준으로 타이머 계산. 서버기준 20 틱마다 서버 시간과 동기화.
    - 서버: 서버 TPS를 기준으로 타이머 계산. 쓰러짐, 사망, 재접속 시 타이머가 정지되어 클라이언트 타이머와 병합하여 보정.
  - 타이머 보정 실패 시 이후 보정을 하지 않도록 수정.


## 4.6.4
2026-02-25
- 수정: DEN, PRN 몹 스폰 정보 수정.
- 추가: PRH 몹 스폰 정보 추가.

## 4.6.3
2026-02-18
- 추가: Feather Client 감지 알림.

## 4.6.3-rc1
2026-02-14
- 추가: Better Zombies Left
  - DER, PRN 몹 스폰 정보 추가.
  - PRH, PRR 헬리콥터 탈출 몹 스폰 정보 추가.
- 수정
  - 잘못된 버전이면 업데이트를 확인하지 않도록 수정.
  - 싱글 플레이에서 작동하지 않도록 수정: 디버그 모드 활성화 필요.
- 코드 수정
  - modFile 객체 초기화 방식.
  - Better Zombies Left의 작동 방식.

## 4.6.3-pre2
2026-02-11
- 추가: Better Zombies Left: DEH 몹 스폰 정보 추가.

## 4.6.3-pre1
2026-02-09
- 추가: Better Zombies Left.
- 코드 수정: Mixin 클래스 리팩토링.

## 4.6.2
2026-01-29
- 추가: Last Weapons가 게임 오버 시에도 작동하는 옵션.
- 수정: Last Weapons가 쿨타임 중인 기술의 아이템 갯수를 표시함.

## 4.6.1
2025-12-23
- 추가: 자동 업데이트로 다운로드한 모드의 무결성 검사.
- 코드 수정: GUI 클래스를 객체로 변경.

## 4.6.0
2025-12-07
- 수정
  - Not Last: 언어별 다른 쉼표 적용. 2명일 때 쉼표 제거
- 추가
  - 디버그 모드: 라운드 통과 시 Not Last 사용할 수 있음

## 4.6.0-rc1
2025-12-04
- 추가: 일본어 번역
- 수정: 영어, 한국어 번역

## 4.6.0-pre1
2025-11-14
- 수정: Powerup Patterns의 자동 타이머 범위 증가.

## 4.6.0-beta2
2025-11-10
- 변경
  - /sla quick 명령어: ZMP에 서술된 하우징 맵 적용.
    - 제거: mogi_a(Bad Blood)
    - 추가: loliepop5(Alien Arcadium)
  - Semi Player Visibility
    - 투명도의 최솟값, 최댓값 설정 추가.
    - 투명도 변화 속도 설정 추가.
      - Linear: 일정한 속도로 변화(기존 방식)
      - Fixed: 최솟값으로 고정
      - Smooth: 부드럽게 변화(이차함수 곡선)
      - Sharp: 빠르게 변화(무리함수 곡선)

## 4.6.0-beta1
2025-10-28
- 추가
  - Recorder
  - 예외 메시지: 게임 및 라운드 시작, Recorder에서 기록 및 저장 시 예외 메시지 추가
- 변경
  - 난이도 감지
    - 스코어보드를 통해 감지(하이픽셀 업데이트)
    - 게임 중간에 생성된 게임 객체의 난이도 감지 동작 추가
  - 디버그 모드: 번역, 텍스트 생삭, 출력 문구에 디테일 추가
- 제거: /zombiesaddon 명령어에서 난이도 변경 기능
- 수정
  - Not Last: 플레이어의 이름 색상을 랭크 색상과 일치
  - 언어 파일 로드를 최초 1회만 실행하도록 변경
- 코드 수정: Tools.kt 개편


## 4.5.3
2025-10-28
- 버그 수정: 키 입력이 무시됨

## 4.5.2
2025-10-12
- 코드 수정
  - LastClientTickEvent 발생 클래스 변경
  - 성능 향상: 컨피그 카테고리 객체의 컬렉션 변경
  - 모드 활성화 옵션 관련 로그 제거
  - 키 입력 감지 함수 호출 방식 변경
- 버그 수정
  - 오타 수정: Zombies Utils 번역 중 오타 수정

## 4.5.1
2025-10-05
- 추가
  - Korean Patcher: Zombies Utils 한국어 번역
- 수정
  - 클라이언트 전용 모드: 모드가 서버에서 로드되지 않도록 수정
- 코드 수정: 번역 키 수정

## 4.5.0
2025-09-30
- 변경
  - 업데이트 체크
    - 번역 문자열 수정
    - 용어 변경
      - 추천 버전 -> 최신 버전
      - 최신 버전 -> 개발 버전
    - 버전 URL 변경
    - 버전 비교 방식 변경
    - 출력 메시지 변경
- 수정
  - mcmod.info: 설명, 크레딧 수정

## 4.5.0-rc2
- Auto Rejoin 수정: 단축키 기본값 변경. 성능 향상.
- 수정: Auto Rejoin 활성화 중 Not Last 비활성화.
- 업데이트 체크 수정: 로그 수정. 주석 추가.

## 4.5.0-rc1
- Auto Splits 수정: /autosplits 명령어에 옵션 추가. 메시지 수정.

## 4.5.0-pre2
- 수정: 더 정확한 타이머 및 Auto Splits. 컨피그 기본값. Auto SLA가 게임 종료 시 SLA를 끔.
- 버그 수정: ZSV의 단축키가 반대로 작동함.
- 코드 수정: 모듈을 상속한 클래스들을 싱글톤 객체로 변경.

## 4.5.0-pre1
- 버그 수정: 잘못된 접두사.
- 로그 추가.

## 4.5.0-beta7
- 버그 수정: Powerup Patterns의 자동 타이머가 작동하지 않음. 게임 시작 시 그동안 입력된 단축키가 감지됨.
- Powerup Patterns 수정: 파워업의 감지 범위 수정.

## 4.5.0-beta6
- 버그 수정: Last Weapons 수정.
- Powerup Patterns 수정: 자동 타이머 키 추가.

## 4.5.0-beta5
- 버그 수정: 버전이 변경되지 않음.

## 4.5.0-beta4
- 버그 수정: 잘못된 게임 제거 타이밍.

## 4.5.0-beta3
- 버그 수정: Prison에서 게임 종료가 되지 않음. 게임 중 모드가 활성화 될 경우 라운드 1부터 시작함.

## 4.5.0-beta2
- 버그 수정: 게임이 종료되지 않음.
- 변경: 게임 종료 시 타이머 정지.

## 4.5.0-beta1
- Internal Timer 수정: 더 정확한 타이머.
- 버그 수정: null 체크 추가. 번역 수정. 로그 메시지를 번역하지 않음.
- Powerup Patterns 수정: 패턴을 게임마다 따로 기록함.
- 추가: /za_debug 명령어와 디버그 모드. Zombies Utils에 한국어 옵션 추가.
- Player Visibility 수정: SST의 Player Invisible과 충돌하지 않도록 수정.
- 변경: 게임 처리 방식을 Zombies Utils에 맞춤.


## 4.4.4
- 버그 수정: 저장된 위치 정보가 불러와지지 않음.

## 4.4.3
- 버그 수정: 모듈의 on/off가 적용되지 않음.

## 4.4.2
- 버그 수정: 잘못된 Mixin으로 컨피그가 열리지 않음.

## 4.4.1
- 버그 수정: Wave Delays에서 잘못된 보스 색상.
- Gui 수정: Gui의 게임 종료 이름을 기존 번역 문자열로 변경.

## 4.4.0
- 버그 수정: Last Weapons에서 기술의 빈 슬롯이 표시되지 않음.

## 4.4.0-rc1
- ZSV 수정: 글을 불러올 때 게임이 정지하지 않도록 수정.
- 버그 수정: 버전을 불러오는데 실패했을 때, 잘못된 버전으로 대체하는 버그 수정.

## 4.4.0-pre4
- 버그 수정: 언어 변경 시 일부 문자열이 충돌을 발생시킴.
- 코드 수정: 일부 try 문을 runCatching 함수로 수정.

## 4.4.0-pre3
- 버그 수정: Wave Delays에서 보스 색상이 누락됨.
- 게임 시작 시 "CustomPlaySound.json"에서 예전 사용법 이었던 "playWave"를 "playType"으로 교체.

## 4.4.0-pre2
- Wave Delays 수정: 잘못된 순서의 접두사 수정.

## 4.4.0-pre1
- 자바를 코틀린으로 변경.
- 버그 수정.
- 코드 개편.
- 컨피그 수정: 옵션의 이름과 설명을 번역.
- Detect Unlegit Mods 수정: 게임 시작 시 GUI로 알림, 자동 모드 삭제 기능 추가.(윈도우 전용)
- 업데이트 체크 수정: 추천 버전과 필수 업데이트를 GUI로 알림, 자동 업데이트 추가.(윈도우 전용)
- 버전 체계 변경.
- Player Visibility 수정: 반투명 범위 조절 가능, 인게임에서만 작동하도록 변경.
- Block Alarm 수정: 살아있지 않은 상태에서 작동하지 않음, 단축키의 기본값을 P로 변경.
- Auto Splits 수정: LiveSplits 전용으로 변경.
- Internal Timer 추가: 기존 Auto Splits의 기능과 동일.
- Wave Delays 수정: 자이언트와 오래된 자의 접두사 수정.
- ZSV 수정: 한글 지원.
- SLA 수정: 창문의 이름과 텍스트 색상 제거 옵션 추가.

## 4.3.2
- 과도한 할당을 줄임.

## 4.3.1
- 버그 수정.

## 4.3.0
- 수정사항 없음.

## 4.3.0-pre3
- 버그 수정.
- /de, /bb, /aa, /pr 명령어 추가.
- Last Weapons 수정.

## 4.3.0-pre2
- 버그 수정.
- Wave Delays 수정.
- Powerup Patterns 수정.

## 4.3.0-pre1
- 버그 수정.
- 명령어 수정.
- 컨피그에 Language 옵션 추가.

## 4.2.3
- 버그 수정.

## 4.2.2
- 버그 수정.
- /zombiesaddon 명령어 추가.
- /info 명령어 제거.

## 4.2.1
- 버그 수정.

## 4.2.0
- 버그 수정.

## 4.2.0-pre6
- 버그 수정.
- Last Weapons 수정.

## 4.2.0-pre5
- 버그 수정.

## 4.2.0-pre4
- 버그 수정.
- /waveDelays, /autoSplits 명령어 추가.
- SST의 언레짓 기능을 차단함.
  <br>SST의 언레짓 기능 차단의 경우 컨피그 파일에서 끌 수 있습니다.

## 4.2.0-pre3
- 버그 수정.
- 여러 언어를 지원하지 않음(4.1.3으로 롤백)
  <br>나중에 추가하는 걸로.

## 4.2.0-pre2
- 버그 수정.

## 4.2.0-pre1
- 코드 최적화.
- 버그 수정.
- Cornering을 Play Visibility로 이름 변경.
- PV 수정.
- Powerup Patterns의 수동 타이머 추가.
- SLA 수정.
- /ZAHUD 명령어 추가.
- Wave Delays 수정.
- SST Setting, Zombies Utils Setting 추가.
- 여러 언어를 지원함.
  <br>SST Setting와 Zombies Utils Setting은 SST의 웨이브 딜레이, Zombies Utils의 타이머를 끄는 컨피그 설정입니다. 기존과 달리 게임을 재시작할 필요가 없습니다.
  /ZAHUD 명령어는 Auto Splits, Wave Delays, Powerup Patterns의 위치를 옮길 수 있는 명령어입니다.


## 4.1.3
- 버그 수정.


## 4.1.2
- 버그 수정.


## 4.1.1
- 버그 수정.
- Auto Rejoin 수정.


## 4.1.0
- Last Weapons 수정.

## 4.1.0-pre6
- 버그 수정.
- Powerup Patterns 수정.

## 4.1.0-pre5
- Last Weapons 수정.
- 버그 수정.

## 4.1.0-pre4
- 버그 수정.

## 4.1.0-pre3
- Text Macro 추가.
- 코드 최적화.
- Last Weapons 수정.

## 4.1.0-pre2
- 버그 수정.

## 4.1.0-pre1
- 버그 수정.
- Powerup Alarm 수정.
- SST Patch 추가.
- Korean Patch 추가.
- Last Weapons 추가.
- Detect Unlegit Mods 추가.
  <br>Detect Unlegit Mods의 경우 컨피그 파일에서 끌 수 있습니다.


## 4.0.1
- 버그 수정.
- SLA 수정.
- Powerup Alarm 수정.
- Wave Delays 수정.


## 4.0.0
- DPS Counter 제거.
- Player Tracker 제거.
- Boss Tracker 제거.
- Gstep Guide 제거.
- Powerup Alarm 제거.
  <br>언레짓 모드가 새로 정의됨에 따라 언레짓 모드를 제거했습니다. Powerup Alarm은 패턴을 표시하는 모드로 변견되었습니다.


## 3.1.0-pre8
- 버그 수정.
- Wave Delays 수정.
- Boss Tracker 수정.
- Player Tracker 수정.
- Grow ESP 제거.
- Grow Guide 제거.
- Boss Spawn Tracker 제거.

## 3.1.0-pre7
- 버그 수정.
- Wave Delays 수정.

## 3.1.0-pre6
- 버그 수정.
- Wave Delays 수정.
- SLA 수정.

## 3.1.0-pre5
- 버그 수정.

## 3.1.0-pre4
- 버그 수정.

## 3.1.0-pre3
- 버그 수정.
- SLA 수정.

## 3.1.0-pre2
- 버그 수정.

## 3.1.0-pre1
- Wave Delays에 Prison맵 추가.
- Prison에서 Boss Wave Alarm, SLA, Boss Spawn Tracker, Boss Tracker가 작동되지 않음.
  <br>Prison에 대한 정확한 정보가 부족하고, 버그를 발생시킬 수 있기에 일부 모드를 Prison에서 작동하지 않도록 했습니다.


## 3.0.0
- 버그 수정.
- Boss Tracker가 보스가 아닌 몹을 표시하는 버그 수정.


## 2.0.0
- 버그 수정.
- Boss Alarm의 이름 수정.
- 닉네임에 "_"가 포함된 경우 일부 기능이 작동하지 않는 버그 수정.


## 1.17.2-pre1
- 버그 수정.
- Player Tracker 추가.
- Boss Tracker 추가.

## 1.17.1
- 버그 수정.

## 1.17.0
- 버그 수정.

## 1.17.0-pre4
- 버그 수정.
- 컨피그 옵션 추가.
- /updatelog 명령어 제거.

## 1.17.0-pre3
- 버그 수정.

## 1.17.0-pre2
- 버그 수정.

## 1.17.0-pre1
- 버그 수정.
- Auto Splits 수정.
- NOTLAST 이름 변경.
- 컨피그 수정.
- Wave Delays 수정.
- Not Last 수정.
  <br>LiveSplit은 잘 사용되지 않기에 Auto Splits에서 LiveSplit을 사용하는 옵션을 제거했습니다.
  이제 Wave Delays와 Grow Guide가 Auto Splits 없이 사용할 수 있습니다.
  게임 오버 시 Auto Splits의 타이머가 리셋이 아닌 정지되며, Wave Delays가 꺼지지 않고 유지됩니다.


## 1.16.0
- 버그 수정.
- Extra Setting 제거.

## 1.16.0-pre2
- 버그 수정.

## 1.16.0-pre1
- 버그 수정.
- Lrod Order 제거.
- Auto Splits 용 RL 모드 추가.
- 업데이트 체크 수정.
- Extra Setting 추가.
- 웨이브 딜레이 수정.
- Powerup Alarm 용 컨피그 제거.


## 1.15.1
- 버그 수정.
- Countdown 제거.

## 1.15.0
- 버그 수정.
- Auto Splits용 Play Sound 추가.


## 1.14.0
- 버그 수정.
- Zombies Utils와 사용할 경우, Zombies Utils의 타이머를 끔.
- Block Alarm의 Health Indicator 제거.

## 1.14.0-pre4
- 버그 수정.

## 1.14.0-pre3
- 버그 수정.

## 1.14.0-pre2
- 버그 수정.
- Gstep Guide 수정.

## 1.14.0-pre1
- Gstep Guide 추가.
- DPS Counter, NOTLAST, Auto Splits, Powerup Alarm 수정.
- 버그 수정.
  <br>DPS Counter, NOTLAST, Auto Splits, Powerup Alarm의 경우 게임 중 on/off를 잘 전환하지 않기에 컨피그로 옮겼습니다.


## 1.13.0
- 버그 수정.
- Grow ESP 추가.
- SST와 사용할 경우, SST의 웨이브 딜레이를 끔.
  <br>SST의 웨이브 딜레이와 Zombies Addon의 웨이브 딜레이가 겹치기에 SST의 웨이브 딜레이를 강제로 끄도록 했습니다. 현재로는 SST의 웨이브 딜레이를 사용할 수 없습니다.


## 1.12.3
- 버그 수정.
- 업데이트 체크 수정.

## 1.12.2
- 업데이트 체크 수정.
- /info 명령어 수정.

## 1.12.1
- Hologram Remover 제거.
- 업데이트 체크 추가.
  <br>싱글/멀티 플레이에 처음 접속하면 채팅창에 업데이트 알림이 뜹니다.

## 1.12.0
- Auto Splits 전용 Grow Guide 추가.
- 버그 수정.
- 컨피그 수정.
- 화면에 모드 버전 추가.
- 모드 업데이트 시 컨피그 리셋.
- 컨피그에서 모드 기본값을 설정하는 옵션 추가.
  <br>이제 컨피그가 변경되는 업데이트가 있을 경우 컨피그가 리셋되어 더 이상 쓰이지 않은 옵션들이 제거됩니다.


## 1.11.0
- Block Alarm 전용 Health Indicator 추가.
- Hologram Bug Generator의 이름을 Hologram Remover로 변경.
- Block Alarm의 rev, dead 제거.


## 1.10.3
- 버그 수정.

## 1.10.2
- Hologram Bug Generator 추가.

## 1.10.1
- 버그 수정.

## 1.10.0
- 패키지 변경.
- 코드 재구성.
- Lrod Order 추가.
- 고급 SLA 추가.
- 명령어 수정.


## 1.9.2
- 버그 수정.

## 1.9.1
- 버그 수정.

## 1.9.0
- 버그 수정.
- Boss Alarm 추가.
- Forge 명령어 버그 수정.
  <br>모드의 명령어가 / 없이 실행되는 버그를 수정했습니다.


## 1.8.9
- 버그 수정.

## 1.8.8
- 버그 수정.
- Auto Splits 전용 Boss Wave Alarm 추가.
  <br>Boss Wave Alarm은 컨피그에서 활성화 가능하며 보스가 스폰하는 웨이브를 표시해줍니다.

## 1.8.7
- 버그 수정.

## 1.8.6
- 버그 수정.
- /zombies 명령어 제거.
- 컨피그에 Zombies Overlay 관련 설정 추가.
- Powerup Alarm 수정.
  <br>명령어보다 컨피그로 사용 여부를 설정하고 자동으로 사용되도록 하는 것이 더 나을 것입니다.

## 1.8.5
- 버그 수정.
- /info 명령어 수정.
- /zombies 명령어 추가.
  <br>/zombies 명령어는 Zombies Overlay를 한국어 환경에서 쓸 수 있도록 하는 명령어입니다.

## 1.8.4
- 버그 수정.
- Auto Splits 수정.

## 1.8.3
- 버그 수정.

## 1.8.2
- 버그 수정.

## 1.8.1
- Powerup Alarm 수정.
- 버그 수정.
- 컨피그에 SLA 관련 설정 추가.
  <br>게임이 시작할 때 자동으로 SLA를 켜주는 설정을 추가했습니다.

## 1.8.0
- Powerup Alarm 추가.
- 버그 수정.
- 컨피그에 Zombies Addon 관련 설정 추가.
- Auto Splits 수정.
  <br>Auto Splits의 기본값이 on으로 변경되었습니다. 오른쪽 위에 표시되는 모드 on/off 여부를 끌 수 있습니다.


## 1.7.6
- 버그 수정.

## 1.7.5
- 버그 수정.

## 1.7.4
- 버그 수정.

## 1.7.3
- 버그 수정.

## 1.7.2
- 버그 수정.

## 1.7.1
- 버그 수정.

## 1.7.0
- Auto Splits 수정.
- /ZSV, /SLA 명령어 수정.
- Auto Rejoin 수정.
- 컨피그에 Auto Splits 관련 설정 추가.
  <br>웨이브 딜레이를 추가했습니다.


## 1.6.5
- 버그 수정.

## 1.6.4
- 버그 수정.

## 1.6.3
- 버그 수정.

## 1.6.2
- NOTLAST, Auto Rejoin 수정.
- 버그 수정.
  <br>NOTLAST, Auto Rejoin을 한국어 환경에서 사용할 수 있습니다.

## 1.6.1
- Block Alarm 수정.
  <br>Block Alarm을 한국어 환경에서 사용할 수 있습니다.

## 1.6.0
- /setMap, /setstrat, /setlines 명령어의 이름을 /SLA, /ZSV, /ZSVLines로 변경.
- /blockAlarm 명령어 제거.
- /cornering 명령어 추가.
- 컨피그에 Cornering, Block Alarm 관련 설정 추가.


## 1.5.2
- 버그 수정.

## 1.5.1
- 버그 수정.

## 1.5.0
- Auto Rejoin 수정.
- 버그 수정.
- Auto Splits 수정.


## 1.4.5
- Block Alarm 수정.
  <br>Block Alarm이 v1.4.2로 롤백됩니다.

## 1.4.4
- Block Alarm 수정.

## 1.4.3
- 버그 수정.
- Block Alarm을 하이픽셀 언어가 한국어일 때 사용 가능.

## 1.4.2
- 버그 수정.
- Block Alarm 수정.
- /blockAlarm 명령어 추가.

## 1.4.1
- 모드의 메시지 수정.

## 1.4.0
- ZSV, SLA 추가.


## 1.3.0
- Auto Splits 추가.


## 1.2.1
- /updatelog 명령어 추가.
- /info 명령어 수정.

## 1.2.0
- NOTLAST 추가.
- /info 명령어 수정.


## 1.1.0
- ZHF 제거.
  <br>ZHF가 사용 금지됨.


## 1.0.0
- ZHF, Cornering, Block Alarm, DPS Counter 추가.
- Block Alarm 수정.
  <br>Block Alarm의 작동 조건을 3명이 다운 될 경우로 수정했습니다.