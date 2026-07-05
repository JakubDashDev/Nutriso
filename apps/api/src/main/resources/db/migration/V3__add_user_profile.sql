CREATE TABLE public.user_profiles (
  id uuid NOT NULL,
  created_at timestamp(6) with time zone NOT NULL,
  updated_at timestamp(6) with time zone NOT NULL,
  date_of_birth date NOT NULL,
  sex character varying(255) NOT NULL,
  height_cm double precision NOT NULL,
  activity_level character varying(255) NOT NULL,
  goal_type character varying(255) NOT NULL,
  weekly_weight_change double precision NOT NULL,
  target_mode character varying(255) NOT NULL,
  manual_kcal_target integer,
  manual_protein_target integer,
  manual_fat_target integer,
  manual_carbs_target integer,
  user_id uuid NOT NULL,
  CONSTRAINT user_profiles_pkey PRIMARY KEY (id),
  CONSTRAINT user_profiles_user_id_key UNIQUE (user_id),
  CONSTRAINT user_profiles_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id),
  CONSTRAINT user_profiles_sex_check CHECK (sex IN ('MALE', 'FEMALE')),
  CONSTRAINT user_profiles_activity_level_check CHECK (activity_level IN ('SEDENTARY', 'LOW_ACTIVE', 'MODERATE', 'HIGH_ACTIVE', 'EXTRA_ACTIVE')),
  CONSTRAINT user_profiles_goal_type_check CHECK (goal_type IN ('LOSE', 'MAINTAIN', 'GAIN')),
  CONSTRAINT user_profiles_target_mode_check CHECK (target_mode IN ('AUTO', 'MANUAL'))
);

CREATE TABLE public.body_measurements (
  id uuid NOT NULL,
  created_at timestamp(6) with time zone NOT NULL,
  updated_at timestamp(6) with time zone NOT NULL,
  weight_kg double precision NOT NULL,
  weight_change double precision NOT NULL DEFAULT 0,
  waist_cm double precision,
  chest_cm double precision,
  hips_cm double precision,
  neck_cm double precision,
  body_fat_percent double precision,
  user_id uuid NOT NULL,
  CONSTRAINT body_measurements_pkey PRIMARY KEY (id),
  CONSTRAINT body_measurements_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id)
);

CREATE INDEX body_measurements_user_created_at_idx
  ON public.body_measurements (user_id, created_at DESC);