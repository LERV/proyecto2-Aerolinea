class CreateFlights < ActiveRecord::Migration[5.0]
  def change
    create_table :flights do |t|
      t.string :id_flight
      t.string :from
      t.string :to
      t.date :date
      t.string :class
      t.string :duration
      t.string :price
      t.string :airplane
      t.string :time_departure
      t.string :time_arrival
      t.string :flight_distance

      t.timestamps
    end
  end
end
