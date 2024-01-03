
 \connect postgres;

CREATE SCHEMA blip_db;

-- Agora, crie a tabela
CREATE TABLE blip_db.eventtracker (
    ownerIdentity     VARCHAR(100) NOT NULL,  
    storageDate       VARCHAR(100) NOT NULL,  
    category          VARCHAR(100) NOT NULL,  
    action            VARCHAR(100) NOT NULL, 
    stateName         VARCHAR(100) NOT NULL,  
    stateId           VARCHAR(100) NOT NULL,  
    messageId         VARCHAR(100) NOT NULL PRIMARY KEY, 
    previousStateId   VARCHAR(100) NOT NULL, 
    previousStateName VARCHAR(100) NOT NULL
);

-- Adicione comentários às colunas
COMMENT ON COLUMN blip_db.eventtracker.ownerIdentity IS 'Identidade do proprietário';
COMMENT ON COLUMN blip_db.eventtracker.storageDate IS 'Data de armazenamento do evento';
COMMENT ON COLUMN blip_db.eventtracker.category IS 'Categoria do evento';
COMMENT ON COLUMN blip_db.eventtracker.action IS 'Ação associada ao evento';
COMMENT ON COLUMN blip_db.eventtracker.stateName IS 'Nome do estado do evento';
COMMENT ON COLUMN blip_db.eventtracker.stateId IS 'Identificador do estado do evento';
COMMENT ON COLUMN blip_db.eventtracker.messageId IS 'Identificador único da mensagem associada ao evento';
COMMENT ON COLUMN blip_db.eventtracker.previousStateId IS 'Identificador do estado anterior ao evento';
COMMENT ON COLUMN blip_db.eventtracker.previousStateName IS 'Nome do estado anterior ao evento';


