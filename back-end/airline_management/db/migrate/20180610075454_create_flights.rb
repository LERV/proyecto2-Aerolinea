class CreateFlights < ActiveRecord::Migration[5.0]
  def change
    create_table :flights do |t|
      t.string :id_flight
      t.string :from
      t.string :to
      t.string :date
      t.integer :duration_hours
      t.string :price
      t.string :airplane_model
      t.string :time_departure
      t.string :time_arrival
      t.string :flight_distance

      t.timestamps
    end
  end
end
