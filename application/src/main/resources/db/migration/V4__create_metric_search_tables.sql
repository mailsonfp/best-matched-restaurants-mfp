CREATE TABLE search_metric (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    search_timestamp TIMESTAMP NOT NULL,
    client_ip VARCHAR(45),
    user_agent TEXT,
    referrer TEXT,
    result_count INTEGER DEFAULT 0,
    other_metadata jsonb
);

CREATE INDEX idx_search_master_timestamp ON search_metric(search_timestamp);

CREATE TABLE search_metric_detail (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    search_id BIGINT NOT NULL,
    param_key VARCHAR(100) NOT NULL,
    param_value TEXT,
    param_type VARCHAR(50),
    CONSTRAINT fk_search FOREIGN KEY (search_id) REFERENCES search_metric(id)
);
