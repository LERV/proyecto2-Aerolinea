class CreateUsers < ActiveRecord::Migration[5.0]
  def change
    create_table :users do |t|
      t.string :name
      t.string :last_name
      t.string :password
      t.string :email
      t.string :profile_picture
      t.string :id_flight

      t.timestamps
    end
  end
end
