json.extract! user, :id, :name, :last_name, :email, :password, :profile_picture, :id_flights, :record_kilometers, :created_at, :updated_at
json.url user_url(user, format: :json)
