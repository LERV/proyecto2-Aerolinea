json.extract! user, :id, :name, :last_name, :password, :email, :profile_picture, :id_flight, :created_at, :updated_at
json.url user_url(user, format: :json)
