json.extract! flight, :id, :id_flight, :from, :to, :date, :class, :duration, :price, :airplane, :time_departure, :time_arrival, :flight_distance, :created_at, :updated_at
json.url flight_url(flight, format: :json)
