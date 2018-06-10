class User < ApplicationRecord
	def password=(val)
    write_attribute(:password, val.reverse)
  end
end
