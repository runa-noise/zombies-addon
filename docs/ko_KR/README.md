![Zombies Addon Logo](../../src/main/resources/logo.png)
# Zombies Addon
다운로드: [최신 버전](https://github.com/runa-noise/zombies-addon/releases/latest), [모든 버전](https://github.com/runa-noise/zombies-addon/releases)

## 지원 언어
- 한국어 (대한민국)
- English (US): Please refer to [this file](../en_US/README.md).
- 日本語 (日本): [このファイル](../ja_JP/README.md)を参照してください。

## 소개
여러 Zombies 모드를 통합하여, 모듈로 제공하는 모드입니다.

## 모듈
### Player Visibility
- 주변 플레이어를 시야에서 숨깁니다.
- 단축키(기본값 Z)로 전환합니다.
- Semi PV는 플레이어를 반투명하게 표시합니다.
- Semi PV는 SST의 Player Invisible의 플레이어 반투명보다
 높은 우선 순위를 가집니다.
- Semi PV 모드는 다음과 같습니다:
  - Linear: 거리에 비례하여 투명도가 변합니다.
  - Fixed: 거리에 상관없이 고정된 투명도(최솟값)를 가집니다.
  - Smooth: 가까워질수록 천천히 투명도가 증가합니다.
  - Sharp: 가까워질수록 빠르게 투명도가 증가합니다.

#### 컨피그
- 전환 기본값: 게임을 시작할 때 전환 기본값입니다.
- 텍스트 표시: Player Visibility 상태를 표시하는 텍스트입니다.
- 범위: Player Visibility의 범위(블록 단위)입니다.
- Semi PV 활성화: Semi Player Visibility를 활성화 또는 비활성화합니다.
- Semi PV 범위: Semi Player Visibility의 범위(블록 단위)입니다.
- Semi PV 투명도 최소값: Semi Player Visibility의 투명도 최소값입니다.
- Semi PV 투명도 최대값: Semi Player Visibility의 투명도 최대값입니다.
- Semi PV 모드: Semi Player Visibility의 모드입니다.

----
### Block Alarm
- 단축키(기본값 P)로 전환합니다.
- 혼자 살아있고 살릴 수 있는 플레이어가 있다면, 화면에 "BLOCK"이 표시됩니다.

#### 컨피그
- 전환 기본값: Block Alarm의 전환 기본값입니다.
- 텍스트 표시: Block Alarm 상태를 표시하는 텍스트입니다.

----
### Not Last
- 마지막 적을 죽인 플레이어를 표시합니다.

----
### Auto Splits
- 라운드 시작 또는 게임 승리 시 LiveSplit에 startorsplit 명령어를 전달합니다.
- 게임 오버 시 LiveSplit에 pause 명령어를 전달합니다.

#### 컨피그
- Auto Splits 활성화: Auto Splits를 활성화 또는 비활성화합니다.
- 호스트: LiveSplit 서버의 호스트입니다.
- 포트: LiveSplit 서버의 포트입니다.

#### 명령어
- autosplits <명령어>: LiveSplit에 명령어를 전달합니다.

#### 사용법
1. [여기](https://livesplit.org/downloads/)에서 LiveSplit을 다운로드합니다.
2. LiveSplit을 실행해 우클릭 → Settings에서 Server Port를 0~65535 중 원하는 숫자(기본값 16834)로 입력합니다.
3. 마인크래프트 실행 → 모드 → Zombies Addon → Config → Auto Splits에서 포트 값을
 2단계에서 입력한 숫자(기본값 16834)를 그대로 입력합니다.
4. LiveSplit 우클릭 → Control → Start TCP Server로 LiveSplit 서버를 시작합니다.
5. 인게임에서 Auto Splits를 키거나 명령어를 사용하면 LiveSplit에 명령어가 전달됩니다.

----
### Internal Timer
- 라운드 시작 시 초기화되는 내부 타이머를 표시합니다.
- Client 모드는 Zombies Addon v4.6.4, Zombies Utils v1.3.7과 동일합니다.
서버 속도와 무관하게 클라이언트를 기준으로 시간을 측정합니다. 서버 기준, 20틱 마다 서버와 동기화됩니다.
- Server 모드는 Zombies Utils v1.4.4을 기반으로 합니다. 서버 속도를 즉시 반영하여 시간을 측정합니다.
재접속 시 서버 속도를 알 수 없게 되어 클라이언트 모드로 전환됩니다. 이후 타이머를 보정하여 다시 서버 모드로 전환됩니다.

#### 컨피그
- Internal Timer 활성화: Internal Timer를 활성화 또는 비활성화합니다.
- 텍스트 스타일: 화면에 표시되는 텍스트의 스타일입니다.
- 모드: Server는 Hypixel 렉을 즉시 반영하지만, 재접속 시 영향을 받을 수 있습니다. Client는 영항을 받지 않습니다.

----
### Wave Delays
- 웨이브 딜레이를 표시합니다.
- 접두사는 웨이브의 특징을 표시합니다. 보스의 경우 웨이브 색으로도 구분할 수 있습니다.
- 텍스트 스타일은 기본값인 "W1: 0.10.0"과 "W1: 0.10.00", "W1 00:10"이 있습니다.
- 웨이브 딜레이의 하이라이트 스타일은 다음과 같습니다:
  - 기본값인 "Zombies Addon"은 3초 후 시작할 웨이브는 노란색, 시작한 웨이브는 녹색,
 시작되지 않은 웨이브는 진한 회색으로 표시되며 현재 웨이브는 보라색 화살표로 표시됩니다.
  - "Zombies Utils"는 Zombies Utils의 웨이브 딜레이와 동일합니다.
- 단축키(기본값 Up)으로 RL Mode를 전환합니다. 웨이브 딜레이가 설정된 틱 만큼 추가되어 표시됩니다.
- 단축키(기본값 Down)으로 Wrath Mode를 전환합니다. 웨이브 딜레이가 설정된 틱 만큼 추가되어 표시됩니다.

#### 컨피그
- Wave Delays 활성화: Wave Delays를 활성화 또는 비활성화합니다.
- 소리 재생: 웨이브 시작 n 틱 후 소리를 재생합니다.
- 커스텀 소리 재생: Json 파일에서 설정한 소리 이름, 피치, 타이밍으로 소리를 재생합니다.
- 접두사: 접두사를 활성화 또는 비활성화합니다.
- 보스 색상: 보스가 등장하는 웨이브의 색상을 활성화 또는 비활성화합니다.
- 텍스트 스타일: 화면에 표시되는 텍스트의 스타일입니다.
- 하이라이트 스타일: 현재 웨이브, 지난 웨이브, 다음 웨이브에 대한 하이라이트 방식입니다.
- 지난 웨이브 숨기기: 화면에서 지나간 웨이브를 숨깁니다.
- RL Mode 오프셋: RL Mode에서 웨이브 딜레이에 오프셋을 추가합니다.
- Wrath Mode 오프셋: Wrath Mode에서 웨이브 딜레이에 오프셋을 추가합니다.

----
### Zombies Strat Viewer
- https://pastebin.com/ 에 있는 글을 화면에 표시합니다.
- 키보드(기본 값 왼쪽 대괄호, 오른쪽 대괄호)로 표시되는 줄을 돌리거나 참여할 수 있습니다.
- 유니코드(한글)을 포함하여 지원합니다.

#### 명령어
- zsv <URL|off>: 글을 표시하거나 끕니다. URL은 "https://pastebin.com/raw/" 로 시작해야 합니다.
- zsvlines <숫자>: 한번에 표시할 줄을 설정합니다.

----
### Spawn Limit Action
- 적이 스폰될 수 있는 창문의 수를 표시합니다.

#### 컨피그
- 자동 SLA: 게임 시작 시 SLA를 자동키며, 게임 종료 시 SLA를 끕니다.
- 텍스트 색상: SLA 텍스트의 색상을 활성화 또는 비활성화합니다.
- 활성화된 창문: SLA에서 활성화된 창문을 표시합니다.
- 비활성화된 창문: SLA에서 비활성화된 창문을 표시합니다.

#### 명령어
- sla <de|bb|aa|off>: 맵을 설정하거나 끕니다.
- sla custom <offset|rotate|mirror>: 하우징과 같은 자작 맵에서 사용하기 위해 SLA의 위치와 방향을 조정합니다.
- sla quick <ghxula|ghxula-garden|loliepop5>: 각 유저의 하우징에 맞도록 SLA를 조정합니다.

----
### Auto Rejoin
- 단축키(기본값 O)로 전환합니다.
- 라운드가 시작할 때 자동으로 재접속합니다.
- 재접속 시도는 5초까지 지속됩니다.

#### 컨피그
- 전환 기본값: 게임 시작 시 Auto Rejoin의 전환 기본값입니다.
- 텍스트 표시: Auto Rejoin 상태를 표시하는 텍스트입니다.

----
### Powerup Patterns
- 파워업이 스폰하는 패턴을 표시합니다.
- 변경, 감지된 패턴은 다음 라운드에서 반영됩니다.
- 예를 들어 라운드 2에 즉시 처치가 스폰한 경우, 라운드 3이 시작되야 화면에 즉시 처치의 패턴이 표시됩니다.
- 단축키들(기본값은 넘버패드 숫자들)로 각 파워업의 타이머를 작동시킵니다.
- 단축키(기본값 F)로 바라보고 있는 30블록 이내의 파워업의 타이머를 작동시킵니다.

#### 명령어
- pow: poweruppatterns의 단축 명령어입니다.
- poweruppatterns <insta|max|ss> \[reset|숫자]: 패턴을 리셋하거나, 직접 설정합니다.
- poweruppatterns <insta|max|ss|dg|carpenter|bg>: 타이머를 작동시킵니다.

----
### Last Weapons
- 게임에서 승리했을 때 가지고 있던 무기와 갑옷을 보여줍니다.

#### 컨피그
- Last Weapons 활성화: Last Weapons를 활성화 또는 비활성화합니다.
- 갑옷 표시: Last Weapons에서 갑옷을 표시합니다.
- 무기 레벨 표시: Last Weapons에서 무기 레벨을 표시합니다.
- 쿨다운된 스킬 표시: Last Weapons에서 쿨다운된 스킬을 표시합니다.
- 게임 오버 시 작동: 게임 오버 시에도 Last Weapons가 작동합니다.

----
### Recorder
- 라운드마다 PB(개인 최고 기록)와 구간 최고 기록을 저장하고 비교합니다.
- 카테고리 별로 기록을 저장하며, 컨피그나 명령어로 카테고리를 지정할 수 있습니다.
- 라운드 통과 시 기존 최고 기록과 비교합니다.
- PB와 구간 최고 기록은 .minecraft/zombies/splits/(카테고리) 폴더에 UTF-16 인코딩된 .times 파일로 저장됩니다.
 하이픽셀이 아니거나 디버그 모드에서는 practise-splits 폴더에 저장됩니다.
- 매 게임마다 구간 기록이 .minecraft/zombies/runs 폴더에 UTF-16 인코딩된 YYYY-MM-DD_HH-MM-SS_(서버 번호).seg 파일로 저장됩니다.
- Zombies Utils의 기록은 비활성화됩니다.
- 저장된 기록은 Zombies Utils에서 사용될 수 있으며, 반대도 가능합니다.
- 연습 서버에서는 기록을 저장하지 않습니다.

#### 컨피그
- Recorder 활성화: Recorder를 활성화 또는 비활성화합니다.
- 기본 카테고리: 명령어를 사용하여 지정하지 않을 때 사용할 카테고리입니다. '/' 또는 '\'가 포함될 수 없습니다.
- PB 알림: 새로운 PB이나 구간 최고 기록이 달성되면 알려줍니다.

#### 명령어
- recorder category \[카테고리]: 현재 카테고리를 확인하거나 설정합니다. '/' 또는 '\'가 포함될 수 없습니다.

----
### Better Zombies Left
- 남은 좀비 수가 현재 웨이브까지의 남은 좀비와 이후 웨이브의 남은 좀비로 나뉘어 표시됩니다.
- 작동 중에는 SST의 Wave 3rd Left Notice가 비활성화됩니다.
- Feather Client 같이 스코어보드를 설정하는 대부분의 모드와 호환되지 않습니다.

----
### Patcher
- 여러 수정 사항을 적용합니다.

#### 컨피그
- 소총 소리 수정: 소총 소리가 들리지 않는 문제를 수정합니다.
- 한글 패치: 인게임에서 잘못된 번역을 수정합니다.
- Zombies Overlay 한글 패치: 한국어 환경에서 [Zombies Overlay](https://github.com/TheExploration/zombies-overlay)를 사용할 수 있습니다.

----
### Text Macro
- 단축키(기본값 Q)를 눌러 채팅에 텍스트를 보냅니다.

----
## 명령어
- zombiesaddon hud: Internal Timer, Wave Delays, Powerup Patterns의 위치를 옮깁니다.
- zombiesaddon checkUpdate: 모드의 업데이트를 확인합니다.
- /de, /bb, /aa, /pr: 각 맵에 접속합니다.
- za_debug: 디버그 모드 관련 명령어입니다.


## 컨피그
### 일반
- 모드 활성화: Zombies Addon 모드를 활성화 또는 비활성화합니다.
- 언어: 모드에 사용할 언어입니다.
- 스피드런 모드: 스피드런 모드를 활성화 또는 비활성화합니다. speedrun.com 규칙에서 금지된 일부 기능을 비활성화합니다.

### 모듈
- 나머지 모듈의 설정입니다.

### 다른 모드
- SST의 스폰 시간 비활성화: SST의 스폰 시간을 비활성화합니다.
- Zombies Utils의 타이머 비활성화: Zombies Utils의 타이머를 비활성화합니다.


## 디버그 모드
- 실제 게임에 접속하지 않고 모드를 테스트할 수 있습니다. 아래 명령어들은 /za_debug 명령어로 시작해야 합니다.
- /za_debug: 디버그 모드를 전환합니다.
- isNotZombies \[false|true]: 대기실 또는 인게임이 아닌지 확인하는 플래그를 확인하거나 설정합니다.
- serverNumber: 현재 접속한 서버 번호를 확인하거나 설정합니다. null은 없음을 의미합니다.
- gameMode \[de|bb|aa|pr] \[normal|hard|rip]: 현재 게임 모드를 확인하거나 설정합니다.
- new: 새 게임을 시작합니다.
- pass <라운드> \[Not Last 플레이어...]: 현재 게임에서 <라운드>를 통과합니다. 플레이어 이름을 추가해서 Not Last를 작동시킵니다.
- helicopter: Prison 맵에서 헬리콥터를 탈출을 시작합니다.
- end <win|lose>: 현재 게임을 승리나 패배로 종료합니다.
- remove: 종료된 게임을 제거합니다.


## 기타
- Zombies Utils의 /sla 명령어가 /sla_zombiesutils로 바뀝니다.
- 최신 버전에 한에 자동 업데이트를 지원합니다.


## 버전 시스템
- 버전은 x.y.z(최신 버전) 또는 x.y.z-타입w(개발 버전)으로 구성됩니다.
- x는 필수 업데이트를 의미합니다. 예를 들어 2.0.0 버전이 최신 버전이라면 그 아래의 버전은 사용할 수 없습니다.
- 버전 타입은 낮은 버전 부터 차례대로 알파(alpha), 베타(beta), 프리 릴리즈(pre), 릴리즈 후보(rc)가 있습니다.
- 알파 버전은 비공개 테스트 버전입니다. 공유하지 마세요.
- 베타 버전은 테스트 버전입니다. 심각한 문제가 있을 수 있습니다.
- 프리 릴리즈 버전은 사소한 버그가 있을 수 있습니다.
- 릴리즈 후보 버전은 최종 테스트 버전입니다.


## 라이선스
이 프로젝트는 [LICENSE](../../LICENSE) 파일의 전문에 따라 MIT 허가서가 적용됩니다.<br>
라이선스 및 저작권 고지 하에 개인적 이용, 수정, 배포, 상업적 이용이 가능하며 보증 및 책임을 지지 않습니다.


## 기여자
[이 파일](CREDITS.md)을 참고하세요.


## 업데이트 로그
[이 파일](CHANGELOG.md)을 참고하세요.
