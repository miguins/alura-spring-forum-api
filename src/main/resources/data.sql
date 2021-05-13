INSERT INTO USUARIO(nome, email, senha) VALUES ('Aluno', 'aluno@email.com', '$2a$10$WUkg0xsWwmZVaXhyEZ/VO.PFy42AOrhYE2ZP28dG01ulhUPZfnQhK');
INSERT INTO USUARIO(nome, email, senha) VALUES ('Mod', 'moderador@email.com', '$2a$10$WUkg0xsWwmZVaXhyEZ/VO.PFy42AOrhYE2ZP28dG01ulhUPZfnQhK');

INSERT INTO PERFIL(id, nome) VALUES (1, 'ROLE_ALUNO');
INSERT INTO PERFIL(id, nome) VALUES (2, 'ROLE_MODERADOR');

INSERT INTO USUARIO_PERFIS(usuario_id, perfis_id) VALUES (1, 1);
INSERT INTO USUARIO_PERFIS(usuario_id, perfis_id) VALUES (2, 2);

INSERT INTO CURSO(nome, categoria) VALUES('Spring BOOT', 'Programação');
INSERT INTO CURSO(nome, categoria) VALUES('HTML 5', 'Front-end');

INSERT INTO TOPICO(titulo, mensagem, data_criacao, status, autor_id, curso_id) VALUES ('Dúvida', 'Erro ao criar projeto', '2019-01-01 19:00:00', 'NAO_RESPONDIDO', 1, 1);
INSERT INTO TOPICO(titulo, mensagem, data_criacao, status, autor_id, curso_id) VALUES ('Dúvida 1', 'Projeto não compila', '2019-01-01 19:00:00', 'NAO_RESPONDIDO', 1, 1);
INSERT INTO TOPICO(titulo, mensagem, data_criacao, status, autor_id, curso_id) VALUES ('Dúvida 2', 'Tag HTML', '2019-01-01 20:00:00', 'NAO_RESPONDIDO', 1, 2);