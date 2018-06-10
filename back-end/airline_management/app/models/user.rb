class User < ApplicationRecord
	def password=(val)
    	write_attribute(:password, val.reverse) #Para encriptar la contrasena, funcion basica guadar string en inversa
  	end
end
