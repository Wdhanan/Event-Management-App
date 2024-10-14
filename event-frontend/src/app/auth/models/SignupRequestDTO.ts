export interface SignupRequestDTO {
  name: string;
  email: string;
  password: string;
  participantRole: 'ADMIN' | 'NORMAL_PARTICIPANT';
}
