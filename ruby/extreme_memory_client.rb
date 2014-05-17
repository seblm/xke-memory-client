require "rest_client"
require "json"
 
ROOT = "http://localhost:3000"
BOARD_SIZE = 2
DELAY = 1.0
 
class Game
  def play
    puts "Start playing"
    pick(Array(0..3))
    sleep(DELAY) if DELAY
  end
 
  def pick(cards)
    puts "pick #{cards}"
    response = RestClient.post "#{ROOT}/play",
               "[[#{cards[0][0]}, #{cards[0][1]}], [#{cards[1][0]}, #{cards[1][1]}]]",
               :content_type => :json
    puts "response: #{response}"
    JSON.parse(response)
  end
end
 
register_status = RestClient.post "#{ROOT}/scores/register", "email@provider.net", :content_type => :json
puts "Registered: #{register_status}"
 
Game.new().play()

