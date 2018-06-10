json.extract! flight, :id, :id_flight, :from, :to, :date, :duration_hours, :price, :airplane_model, :time_departure, :time_arrival, :flight_distance, :created_at, :updated_at
json.url flight_url(flight, format: :json)
