-- Insert roles with conflict handling
INSERT INTO roles(name) 
VALUES ('ROLE_USER'), ('ROLE_MANAGER'), ('ROLE_ADMIN')
ON CONFLICT (name) DO NOTHING;

-- Add a default admin user (BCrypt encoded password)
INSERT INTO users (name, email, password)
VALUES (
  'Admin User',
  'admin@floral.com',
  '$2a$10$2.VD5AgeKu9c96HlbXTRtOBOeD/L5W8vf7VVnvgV1a9HkR0gYy2gG' -- 'password123'
)
ON CONFLICT (email) DO NOTHING;

-- Assign admin role to admin user
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u, roles r
WHERE u.email = 'admin@floral.com' AND r.name = 'ROLE_ADMIN'
ON CONFLICT (user_id, role_id) DO NOTHING;

-- Sample categories
--INSERT INTO categories (name)
--VALUES
--  ('Roses'),
--  ('Bouquets'),
--  ('Decorative Plants'),
--  ('Garden Flowers')
--ON CONFLICT (name) DO NOTHING;
