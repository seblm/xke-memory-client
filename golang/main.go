package main

import (
  "fmt"
  "net/http"
  "strings"
  "encoding/json"
  "io/ioutil"
)

// {
//   "gameId": 1,                      // game round
//   "progress": 5.0,                  // game progress, in percents
//   "turn": {
//     "turnScore": 0,                 // score in this turn
//     "cards": [
//       {
//         "symbol": "coat",           // first card played
//         "color": "blue",
//         "found": false
//       },
//       {
//         "symbol": "dog",            // second card played
//         "color": "green",
//         "found": false
//       }
//     ],
//     "message": null                 // warn message if exists
//   },
//   "gameScore": 7                    // total score of the game
// }

type Client struct {
	Host string
}

type Pos struct {
	x, y int
}

func (client *Client) Register(Email string) {
	http.Post(fmt.Sprintf("%s%s", client.Host, "/scores/register"),
		      "application/json",
		      strings.NewReader(Email))
}

func (client *Client) Play(pos1, pos2 Pos) *Play {
	resp, err := http.Post(fmt.Sprintf("%s%s", client.Host, "/play"),
		                    "application/json",
		                    strings.NewReader(fmt.Sprintf("[ [%d, %d], [%d, %d] ]", pos1.x, pos1.y, pos2.x, pos2.y)))
	if err != nil {
	    // handle error
	    return nil
    }
    var play Play
    data, err := ioutil.ReadAll(resp.Body)
    json.Unmarshal(data, &play)
    return &play
}

type Play struct {
	GameId    int
	Progress  int
	Turn      Turn
	GameScore int
}

type Turn struct {
	TurnScore int
	Message   string
	Cards     []Card
}

type Card struct {
	Symbol string
	Color  string
	Found  bool
}

const (
	SIZE = 32
)

func main() {
	client := &Client{"http://localhost:3000"}
	fmt.Print("Hello World")
	client.Register("some email")
	fmt.Print("Hello World")

	clothed := make([]Pos, SIZE*SIZE)
	for i := 0; i < SIZE*SIZE; i++ {
		clothed[i] = Pos{i / SIZE, i % SIZE}
	}

	naked := map[string]Pos{}

	for len(clothed) > 0 {
		p1, p2 := clothed[0], clothed[1]
		clothed = clothed[2:]

		play := client.Play(p1, p2)

		if existing := addCard(naked, play.Turn.Cards[0], p1); existing != nil {
			client.Play(p1, *existing)
		}
		if existing := addCard(naked, play.Turn.Cards[1], p2); existing != nil {
			client.Play(p2, *existing)
		}

	}
}

func addCard(to map[string]Pos, card Card, pos Pos) *Pos {
	key := cardKey(card)
	if existing, ok := to[key]; ok {
		return &existing
	}
	to[key] = pos
	return nil
}
func cardKey(card Card) string {
	return fmt.Sprintf("%s:%s", card.Color, card.Symbol)
}
