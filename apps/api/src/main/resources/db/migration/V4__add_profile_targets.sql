CREATE TABLE public.profile_targets (
  id uuid NOT NULL,
  created_at timestamp(6) with time zone NOT NULL,
  updated_at timestamp(6) with time zone NOT NULL,
  activity_level character varying(255) NOT NULL,
  goal_type character varying(255) NOT NULL,
  weekly_weight_change double precision NOT NULL,
  target_mode character varying(255) NOT NULL,
  manual_kcal_target integer,
  manual_protein_target integer,
  manual_fat_target integer,
  manual_carbs_target integer,
  user_id uuid NOT NULL,
  CONSTRAINT profile_targets_pkey PRIMARY KEY (id),
  CONSTRAINT profile_targets_user_id_key UNIQUE (user_id),
  CONSTRAINT profile_targets_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id),
  CONSTRAINT profile_targets_activity_level_check CHECK (activity_level IN ('SEDENTARY', 'LOW_ACTIVE', 'MODERATE', 'HIGH_ACTIVE', 'EXTRA_ACTIVE')),
  CONSTRAINT profile_targets_goal_type_check CHECK (goal_type IN ('LOSE', 'MAINTAIN', 'GAIN')),
  CONSTRAINT profile_targets_target_mode_check CHECK (target_mode IN ('AUTO', 'MANUAL'))
);

INSERT INTO public.profile_targets (
  id,
  created_at,
  updated_at,
  activity_level,
  goal_type,
  weekly_weight_change,
  target_mode,
  manual_kcal_target,
  manual_protein_target,
  manual_fat_target,
  manual_carbs_target,
  user_id
)
SELECT
  id,
  created_at,
  updated_at,
  activity_level,
  goal_type,
  CASE
    WHEN goal_type = 'LOSE' THEN -GREATEST(ABS(weekly_weight_change), 0.1::double precision)
    ELSE weekly_weight_change
  END,
  target_mode,
  manual_kcal_target,
  manual_protein_target,
  manual_fat_target,
  manual_carbs_target,
  user_id
FROM public.user_profiles;

ALTER TABLE public.user_profiles
  DROP COLUMN activity_level,
  DROP COLUMN goal_type,
  DROP COLUMN weekly_weight_change,
  DROP COLUMN target_mode,
  DROP COLUMN manual_kcal_target,
  DROP COLUMN manual_protein_target,
  DROP COLUMN manual_fat_target,
  DROP COLUMN manual_carbs_target;
