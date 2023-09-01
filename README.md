# DeeDee

The browser online game with characters and real-time battles.

## Design

The DeeDee design is available [at Figma](https://www.figma.com/file/LcKRODbnjbedwppjfD2PUh/deedee?type=design&node-id=0%3A1&mode=design&t=qFIeiHn7mtQhtlen-1).

## Implemented functionality

- Authorization though Telegram bot
- Character selection
- Real-time notifications:
  - Battles result
- Two currencies:
  - VIP currency
  - Regular currency
- Real-time chat:
  - Online players block
  - Messages block
- Market:
  - Basic boosters (regular currency)
  - VIP attacks (VIP currency)
- Giveaways:
  - Regular currency giveaways
  - VIP currency giveaways
- Ratings:
  - Rating system
- Profile:
  - User statistics
  - Settings VIP attacks
- Real-time battles:
  - Choose battle currency
  - Updates active battles
  - Full information about battle
  - Real-time battle actions
  - Update user info after each battle
  - Random battle actions
  - Use unique attacks in battles
- #### **Dynamic (Bots)**
  - Available to add list of bots:
    - Avatars
    - Nickname
  - Scheduled upgrade of bots character
  - Scheduled joining to battles by bots
  - Scheduled sending messages from bots:
    - Available to add list of messages

## Tech Stack

- Java 17
- Gradle
- Spring Framework:
  - Spring Web
  - Spring Security
  - Spring WebSocket
  - Spring Data Jpa
  - Spring Configuration Processor
  - Validation
  - Lombok
  - PostgreSQL
  - Spring Kafka
  - Auth0
- PostgreSQL 15
- Kafka + zookeeper
- Docker

## Deployment

Clone repository:
```bash
git clone https://github.com/dbezk/deedee.git
```

Go to «login» folder:
```bash
cd login/
```

Open properties file through nano and change `bot.token=<bot_token>` to your Telegram Bot Token:
```bash
nano src/main/resources/application.properties
```

Build .jar:
```bash
$./gradlew bootJar
```

Go to «deedee» folder:
```bash
cd .. && cd deedee/
```

Build .jar:
```bash
$./gradlew bootJar
```

Go to root folder:
```bash
cd ..
```

Create docker images:
```bash
docker build -t main-server -f Dockerfile_main .
docker build -t login-server -f Dockerfile_login .
```

Run the Docker container:
```bash
docker compose up -d
```

####  Successful deployment!

## License

[MIT](https://choosealicense.com/licenses/mit/)