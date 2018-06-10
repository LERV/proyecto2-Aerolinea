# This file is auto-generated from the current state of the database. Instead
# of editing this file, please use the migrations feature of Active Record to
# incrementally modify your database, and then regenerate this schema definition.
#
# Note that this schema.rb definition is the authoritative source for your
# database schema. If you need to create the application database on another
# system, you should be using db:schema:load, not running all the migrations
# from scratch. The latter is a flawed and unsustainable approach (the more migrations
# you'll amass, the slower it'll run and the greater likelihood for issues).
#
# It's strongly recommended that you check this file into your version control system.

ActiveRecord::Schema.define(version: 20180610075454) do

  create_table "flights", force: :cascade do |t|
    t.string   "id_flight"
    t.string   "from"
    t.string   "to"
    t.string   "date"
    t.integer  "duration_hours"
    t.string   "price"
    t.string   "airplane_model"
    t.string   "time_departure"
    t.string   "time_arrival"
    t.string   "flight_distance"
    t.datetime "created_at",      null: false
    t.datetime "updated_at",      null: false
  end

  create_table "users", force: :cascade do |t|
    t.string   "name"
    t.string   "last_name"
    t.string   "email"
    t.string   "password"
    t.string   "profile_picture"
    t.string   "id_flights"
    t.integer  "record_kilometers"
    t.datetime "created_at",        null: false
    t.datetime "updated_at",        null: false
  end

end
